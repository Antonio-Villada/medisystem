package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.exceptions.CitaConflictoHorarioException;
import medisystem.avanzada.uq.citas_service.exceptions.CitaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.exceptions.PacienteNoEncontradoException;
import medisystem.avanzada.uq.citas_service.mappers.CitaMapper;
import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
import medisystem.avanzada.uq.citas_service.repositories.PacienteRepository;
import medisystem.avanzada.uq.citas_service.services.CitaService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("dbCitaService")
@Transactional
public class CitaServiceImpl implements CitaService {

    private static final LocalTime HORA_INICIO_CONSULTA = LocalTime.of(8, 0);
    private static final LocalTime HORA_FIN_CONSULTA = LocalTime.of(17, 0);
    private static final int LIMITE_CITAS_DIARIAS = 10;

    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final CitaMapper citaMapper;
    // Inyección por Constructor
    public CitaServiceImpl(CitaRepository citaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository,
                           CitaMapper citaMapper) {

        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaMapper = citaMapper;
    }


    private String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

    private void verificarAcceso(Cita cita) {
        String username = getAuthenticatedUsername();
        if (username == null) return;

        boolean esPaciente = cita.getPaciente().getUsuario().getUsername().equals(username);
        boolean esMedico = cita.getMedico().getUsuario().getUsername().equals(username);
        if (!esPaciente && !esMedico) {
            throw new AccessDeniedException("No tienes permiso para acceder a esta cita.");
        }
    }



    @Override
    public CitaResponseDTO agendarCita(CitaRequestDTO dto) {
        // 1. Validar y Buscar Dependencias (Médico y Paciente)
        Medico medico = medicoRepository.findById(dto.getIdMedico())
                .orElseThrow(() -> new MedicoNoEncontradoException(dto.getIdMedico()));
        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new PacienteNoEncontradoException(dto.getIdPaciente()));
// 2. Seguridad: Solo el paciente autenticado puede agendar su propia cita
        String username = getAuthenticatedUsername();
        if (!paciente.getUsuario().getUsername().equals(username)) {
            throw new AccessDeniedException("Solo puedes agendar tus propias citas.");
        }

        // 3. Aplicar Reglas de Agendamiento
        validarReglasDeAgendamiento(medico, dto);
// 4. Mapear y Guardar
        Cita nuevaCita = citaMapper.toEntity(dto, medico, paciente);
        Cita savedCita = citaRepository.save(nuevaCita);

        // 5. Retornar DTO
        return citaMapper.toResponseDTO(savedCita);
    }



    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> getAllCitas() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponseDTO getCitaById(Long idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException(idCita));
        verificarAcceso(cita);

        return citaMapper.toResponseDTO(cita);
    }

    @Override
    @Transactional(readOnly = true)
    // Devuelve el historial de citas de un paciente.
    public List<CitaResponseDTO> findByPaciente(Paciente paciente) {
        // Usa el repositorio para obtener las citas por entidad, luego mapea a DTO
        return citaRepository.findByPaciente(paciente).stream()
                .map(citaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    // Devuelve la agenda de citas de un médico.
    public List<CitaResponseDTO> findByMedico(Medico medico) {
        // Usa el repositorio para obtener las citas por entidad, luego mapea a DTO
        return citaRepository.findByMedico(medico).stream()
                .map(citaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public CitaResponseDTO updateCita(Long idCita, CitaRequestDTO dto) {
        Cita citaExistente = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException(idCita));
        verificarAcceso(citaExistente);

        // Validar la nueva hora y fecha
        validarReglasDeAgendamiento(citaExistente.getMedico(), dto);
// Actualización completa
        citaExistente.setFecha(dto.getFecha());
        citaExistente.setHoraInicio(dto.getHoraInicio());
        citaExistente.setObservaciones(dto.getObservaciones());
// La duración es de 1 hora
        citaExistente.setHoraFin(dto.getHoraInicio().plusHours(1));

        Cita updatedCita = citaRepository.save(citaExistente);
        return citaMapper.toResponseDTO(updatedCita);
    }

    @Override
    public CitaResponseDTO patchCita(Long idCita, CitaRequestDTO dto) {
        Cita existente = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException(idCita));
        verificarAcceso(existente);

        // Actualización parcial
        if (dto.getFecha() != null) existente.setFecha(dto.getFecha());
        if (dto.getHoraInicio() != null) {
            existente.setHoraInicio(dto.getHoraInicio());
            existente.setHoraFin(dto.getHoraInicio().plusHours(1));
        }
        if (dto.getObservaciones() != null) existente.setObservaciones(dto.getObservaciones());
// Volver a validar reglas si se modificó el horario
        if (dto.getFecha() != null || dto.getHoraInicio() != null) {
            validarReglasDeAgendamiento(existente.getMedico(), dto);
        }

        Cita updatedCita = citaRepository.save(existente);
        return citaMapper.toResponseDTO(updatedCita);
    }

    @Override
    public void deleteCita(Long idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException(idCita));
        verificarAcceso(cita);

        // Regla de negocio: solo se puede cancelar con un día de anticipación
        if (!cita.getFecha().isAfter(LocalDate.now())) {
            throw new CitaConflictoHorarioException("Solo puede cancelar citas con al menos un día de anticipación.");
        }

        citaRepository.delete(cita);
    }

    // ==========================================================
    // MÉTODOS PRIVADOS DE VALIDACIÓN
    // ==========================================================

    private void validarReglasDeAgendamiento(Medico medico, CitaRequestDTO dto) {

        if (dto.getFecha().isBefore(java.time.LocalDate.now())) {
            throw new CitaConflictoHorarioException("No se pueden agendar citas en fechas pasadas.");
        }

        if (dto.getFecha().getDayOfWeek() == DayOfWeek.SATURDAY ||
                dto.getFecha().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new CitaConflictoHorarioException("No se pueden agendar citas los fines de semana.");
        }

        // Asumiendo que la duración de la cita es de 1 hora
        LocalTime horaFinEstimada = dto.getHoraInicio().plusHours(1);
        if (dto.getHoraInicio().isBefore(HORA_INICIO_CONSULTA) ||
                horaFinEstimada.isAfter(HORA_FIN_CONSULTA.plusHours(1))) {
            throw new CitaConflictoHorarioException("Las citas deben ser agendadas entre las 8:00 y 17:00.");
        }

        // Validación de conflicto de horario (Médico ocupado)
        boolean yaExiste = citaRepository.existsByMedicoAndFechaAndHoraInicio(
                medico, dto.getFecha(), dto.getHoraInicio());
        if (yaExiste) {
            throw new CitaConflictoHorarioException(
                    "El Dr/a. " + medico.getNombreMedico() + " ya tiene una cita agendada a las " +
                            dto.getHoraInicio() + " el día " + dto.getFecha() + ".");
        }

        // Validación de límite de citas diarias (Máximo 10)
        long citasDia = citaRepository.countByMedicoAndFecha(medico, dto.getFecha());
        if (citasDia >= LIMITE_CITAS_DIARIAS) {
            throw new CitaConflictoHorarioException(
                    "El Dr/a. " + medico.getNombreMedico() + " ha alcanzado el límite de " + LIMITE_CITAS_DIARIAS + " citas para el día " + dto.getFecha() + ".");
        }
    }
}
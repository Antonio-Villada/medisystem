package medisystem.avanzada.uq.citas_service.service.impl;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.exceptions.CitaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.mappers.CitaMapper;
import medisystem.avanzada.uq.citas_service.mappers.MedicoMapper;
import medisystem.avanzada.uq.citas_service.mappers.PacienteMapper;
import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
import medisystem.avanzada.uq.citas_service.repositories.PacienteRepository;
import medisystem.avanzada.uq.citas_service.service.CitaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("dbCitaService")
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final CitaMapper citaMapper;
    private final MedicoMapper medicoMapper;
    private final PacienteMapper pacienteMapper;

    public CitaServiceImpl(CitaRepository citaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository,
                           CitaMapper citaMapper,
                           MedicoMapper medicoMapper,
                           PacienteMapper pacienteMapper) {
        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaMapper = citaMapper;
        this.medicoMapper = medicoMapper;
        this.pacienteMapper = pacienteMapper;
    }

    @Override
    public List<CitaResponseDTO> getCitas() {
        return citaRepository.findAll()
                .stream()
                .map(cita -> {
                    MedicoResponseDTO medicoDTO = medicoMapper.toResponseDTO(cita.getMedico());
                    PacienteResponseDTO pacienteDTO = pacienteMapper.toDTO(cita.getPaciente(), null);
                    return citaMapper.toResponseDTO(cita, medicoDTO, pacienteDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CitaResponseDTO getCitaById(Integer idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));
        MedicoResponseDTO medicoDTO = medicoMapper.toResponseDTO(cita.getMedico());
        PacienteResponseDTO pacienteDTO = pacienteMapper.toDTO(cita.getPaciente(), null);
        return citaMapper.toResponseDTO(cita, medicoDTO, pacienteDTO);
    }

    @Override
    public CitaResponseDTO postCita(CitaRequestDTO dto) {
        Medico medico = medicoRepository.findById(dto.getIdMedico())
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        Cita cita = citaMapper.toEntity(dto, medico, paciente);

        // ---- VALIDACIONES ORIGINALES ----
        if (cita.getHoraInicio() == null) {
            throw new IllegalArgumentException("Debe especificar la hora de inicio de la cita.");
        }

        LocalTime horaInicio = cita.getHoraInicio();
        LocalTime horaFin = cita.getHoraFin() != null ? cita.getHoraFin() : horaInicio.plusHours(1);

        if (horaInicio.isBefore(LocalTime.of(8, 0)) || horaFin.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("Las citas solo pueden programarse entre 8:00 a.m. y 6:00 p.m.");
        }

        if (!horaFin.equals(horaInicio.plusHours(1))) {
            throw new IllegalArgumentException("Cada cita debe tener una duración de una hora.");
        }

        LocalDate hoy = LocalDate.now();
        if (cita.getFecha().isBefore(hoy) || cita.getFecha().isAfter(hoy.plusDays(6))) {
            throw new IllegalArgumentException("Solo se pueden agendar citas para los próximos 6 días.");
        }

        int citasMedicoDia = citaRepository.countByMedicoAndFecha(cita.getMedico(), cita.getFecha());
        if (citasMedicoDia >= 10) {
            throw new IllegalArgumentException("El médico ya tiene el máximo de 10 citas para este día.");
        }

        boolean ocupado = citaRepository.existsByMedicoAndFechaAndHoraInicio(
                cita.getMedico(), cita.getFecha(), cita.getHoraInicio()
        );
        if (ocupado) {
            throw new IllegalArgumentException("El médico ya tiene una cita programada en ese horario.");
        }

        // ---- GUARDAR Y RETORNAR ----
        cita.setHoraFin(horaFin);
        Cita guardada = citaRepository.save(cita);

        MedicoResponseDTO medicoDTO = medicoMapper.toResponseDTO(guardada.getMedico());
        PacienteResponseDTO pacienteDTO = pacienteMapper.toDTO(guardada.getPaciente(), null);
        return citaMapper.toResponseDTO(guardada, medicoDTO, pacienteDTO);
    }

    // ------------------ MÉTODOS ORIGINALES SIN CAMBIOS ------------------
    @Override
    public Cita putCita(Integer idCita, Cita cita) {
        Cita citaExistente = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));
        boolean conflicto = citaRepository.existsByMedicoAndFechaAndHoraInicio(
                cita.getMedico(), cita.getFecha(), cita.getHoraInicio());
        if (conflicto && !citaExistente.getIdCita().equals(cita.getIdCita())) {
            throw new IllegalArgumentException("El médico ya tiene una cita programada en ese horario.");
        }
        citaExistente.setFecha(cita.getFecha());
        citaExistente.setHoraInicio(cita.getHoraInicio());
        citaExistente.setHoraFin(cita.getHoraInicio().plusHours(1));
        citaExistente.setMedico(cita.getMedico());
        citaExistente.setPaciente(cita.getPaciente());
        citaExistente.setObservaciones(cita.getObservaciones());
        citaExistente.setFormula(cita.getFormula());
        return citaRepository.save(citaExistente);
    }

    @Override
    public void deleteCita(Integer idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));
        if (!cita.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Solo puede cancelar citas con al menos un día de anticipación.");
        }
        citaRepository.delete(cita);
    }

    @Override
    public Cita patchCita(Integer idCita, Cita cita) {
        Cita existente = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));
        if (cita.getFecha() != null) existente.setFecha(cita.getFecha());
        if (cita.getMedico() != null) existente.setMedico(cita.getMedico());
        if (cita.getPaciente() != null) existente.setPaciente(cita.getPaciente());
        if (cita.getObservaciones() != null) existente.setObservaciones(cita.getObservaciones());
        if (cita.getFormula() != null) existente.setFormula(cita.getFormula());
        return citaRepository.save(existente);
    }
}

package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicoYaExisteException;
import medisystem.avanzada.uq.citas_service.mappers.MedicoMapper;
import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
import medisystem.avanzada.uq.citas_service.repositories.EspecialidadRepository;
import medisystem.avanzada.uq.citas_service.services.MedicoService;
import medisystem.avanzada.uq.citas_service.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;
    private final UserService userService; // Inyección para la lógica de Usuario
    private final MedicoMapper medicoMapper; // Inyección del Mapper

    public MedicoServiceImpl(MedicoRepository medicoRepository,
                             EspecialidadRepository especialidadRepository,
                             UserService userService,
                             MedicoMapper medicoMapper) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
        this.userService = userService;
        this.medicoMapper = medicoMapper;
    }

    // ========================================================
    // CREACIÓN Y REGISTRO (POST)
    // ========================================================

    @Override
    public MedicoResponseDTO registrarMedico(MedicoRequestDTO dto) {
        // Validación 1: El correo no debe existir
        if (medicoRepository.existsByCorreo(dto.getCorreo())) {
            throw new MedicoYaExisteException(dto.getCorreo(), true);
        }

        // Validación 2: El usuario no debe existir (Delegado a UserService)

        // 1. Crear Usuario (Delegado al UserService para encriptación y rol)
        Usuario usuario = userService.crearNuevoUsuario(
                dto.getUsername(),
                dto.getPassword(),
                RolNombre.MEDICO
        );

        // 2. Buscar Especialidad
        Especialidad especialidad = especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new EspecialidadNoEncontradaException(dto.getIdEspecialidad()));

        // 3. Crear Entidad Medico (Usando el Mapper)
        Medico medico = medicoMapper.toEntity(dto, usuario, especialidad);
        // NOTA: El ID del médico es autogenerado (Long), no se toma del DTO.

        Medico savedMedico = medicoRepository.save(medico);

        // 4. Retornar DTO de salida
        return medicoMapper.toResponseDTO(savedMedico);
    }

    // ========================================================
    // LECTURA (GET)
    // ========================================================

    @Override
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> getAllMedicos() {
        return medicoRepository.findAll().stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MedicoResponseDTO getMedicoById(Long idMedico) {
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(idMedico));

        return medicoMapper.toResponseDTO(medico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> getMedicosByEspecialidadId(Long idEspecialidad) {
        Especialidad especialidad = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new EspecialidadNoEncontradaException(idEspecialidad));

        // Usa el método optimizado del repositorio (findByEspecialidad)
        return medicoRepository.findByEspecialidad(especialidad).stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ========================================================
    // ACTUALIZACIÓN (PUT/PATCH)
    // ========================================================

    @Override
    public MedicoResponseDTO updateMedico(Long idMedico, MedicoRequestDTO dto) {
        Medico existente = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(idMedico));

        // 1. Validar que el correo no se duplique con otro médico
        if (medicoRepository.existsByCorreo(dto.getCorreo()) &&
                !existente.getCorreo().equalsIgnoreCase(dto.getCorreo())) {
            throw new MedicoYaExisteException(dto.getCorreo(), true);
        }

        // 2. Actualizar Especialidad (Requiere búsqueda)
        Especialidad nuevaEspecialidad = especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new EspecialidadNoEncontradaException(dto.getIdEspecialidad()));

        // 3. Actualizar campos
        existente.setNombreMedico(dto.getNombreMedico());
        existente.setTelefono(dto.getTelefono());
        existente.setCorreo(dto.getCorreo());
        existente.setEspecialidad(nuevaEspecialidad);

        // 4. NOTA: La actualización del Usuario (username/password) debe ser un método separado en UserService.

        Medico updatedMedico = medicoRepository.save(existente);

        // 5. Retornar DTO
        return medicoMapper.toResponseDTO(updatedMedico);
    }

    @Override
    public MedicoResponseDTO patchMedico(Long idMedico, MedicoRequestDTO dto) {
        Medico existente = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(idMedico));

        // Aquí se requiere lógica compleja de mapeo nulo que MapStruct no hace fácilmente con un DTO.
        // Se implementa manualmente la lógica de PATCH (solo actualiza si el campo no es nulo).

        if (dto.getNombreMedico() != null) {
            existente.setNombreMedico(dto.getNombreMedico());
        }
        if (dto.getTelefono() != null) {
            existente.setTelefono(dto.getTelefono());
        }
        if (dto.getCorreo() != null) {
            // Validar correo en PATCH
            if (medicoRepository.existsByCorreo(dto.getCorreo()) &&
                    !existente.getCorreo().equalsIgnoreCase(dto.getCorreo())) {
                throw new MedicoYaExisteException(dto.getCorreo(), true);
            }
            existente.setCorreo(dto.getCorreo());
        }
        if (dto.getIdEspecialidad() != null) {
            Especialidad nuevaEspecialidad = especialidadRepository.findById(dto.getIdEspecialidad())
                    .orElseThrow(() -> new EspecialidadNoEncontradaException(dto.getIdEspecialidad()));
            existente.setEspecialidad(nuevaEspecialidad);
        }

        // NOTA: Los campos de usuario (username, password) NO se manejan aquí.

        Medico updatedMedico = medicoRepository.save(existente);
        return medicoMapper.toResponseDTO(updatedMedico);
    }

    // ========================================================
    // ELIMINACIÓN (DELETE)
    // ========================================================

    @Override
    public void deleteMedico(Long idMedico) {
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(idMedico));

        medicoRepository.delete(medico);
        // El borrado del Usuario asociado debería ocurrir por la configuración de Cascade en la entidad Medico.
    }
}
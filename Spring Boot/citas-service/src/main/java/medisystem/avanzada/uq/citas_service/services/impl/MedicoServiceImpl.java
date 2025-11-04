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
    private final UserService userService;
    private final MedicoMapper medicoMapper;

    public MedicoServiceImpl(MedicoRepository medicoRepository,
                             EspecialidadRepository especialidadRepository,
                             UserService userService,
                             MedicoMapper medicoMapper) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
        this.userService = userService;
        this.medicoMapper = medicoMapper;
    }


    @Override
    public MedicoResponseDTO registrarMedico(MedicoRequestDTO dto) {
        if (medicoRepository.existsByCorreo(dto.getCorreo())) {
            throw new MedicoYaExisteException(dto.getCorreo(), true);
        }


        Usuario usuario = userService.crearNuevoUsuario(
                dto.getUsername(),
                dto.getPassword(),
                RolNombre.MEDICO
        );

        Especialidad especialidad = especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new EspecialidadNoEncontradaException(dto.getIdEspecialidad()));

        Medico medico = medicoMapper.toEntity(dto, usuario, especialidad);
        Medico savedMedico = medicoRepository.save(medico);
        return medicoMapper.toResponseDTO(savedMedico);
    }


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

        return medicoRepository.findByEspecialidad(especialidad).stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public MedicoResponseDTO updateMedico(Long idMedico, MedicoRequestDTO dto) {
        Medico existente = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(idMedico));

        if (medicoRepository.existsByCorreo(dto.getCorreo()) &&
                !existente.getCorreo().equalsIgnoreCase(dto.getCorreo())) {
            throw new MedicoYaExisteException(dto.getCorreo(), true);
        }

        Especialidad nuevaEspecialidad = especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new EspecialidadNoEncontradaException(dto.getIdEspecialidad()));

        existente.setNombreMedico(dto.getNombreMedico());
        existente.setTelefono(dto.getTelefono());
        existente.setCorreo(dto.getCorreo());
        existente.setEspecialidad(nuevaEspecialidad);


        Medico updatedMedico = medicoRepository.save(existente);

        return medicoMapper.toResponseDTO(updatedMedico);
    }

    @Override
    public MedicoResponseDTO patchMedico(Long idMedico, MedicoRequestDTO dto) {
        Medico existente = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(idMedico));


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


        Medico updatedMedico = medicoRepository.save(existente);
        return medicoMapper.toResponseDTO(updatedMedico);
    }


    @Override
    public void deleteMedico(Long idMedico) {
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(idMedico));

        medicoRepository.delete(medico);
    }
}
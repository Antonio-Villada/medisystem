package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadYaExisteException; // Excepción necesaria
import medisystem.avanzada.uq.citas_service.mappers.EspecialidadMapper;
import medisystem.avanzada.uq.citas_service.repositories.EspecialidadRepository;
import medisystem.avanzada.uq.citas_service.services.EspecialidadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("dbEspecialidadService")
@Transactional
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadMapper especialidadMapper;

    public EspecialidadServiceImpl(EspecialidadRepository especialidadRepository,
                                   EspecialidadMapper especialidadMapper) {
        this.especialidadRepository = especialidadRepository;
        this.especialidadMapper = especialidadMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadResponseDTO> getAllEspecialidades() {
        return especialidadRepository.findAll().stream()
                .map(especialidadMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EspecialidadResponseDTO getEspecialidadById(Long idEspecialidad) {
        Especialidad especialidad = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new EspecialidadNoEncontradaException(idEspecialidad));

        return especialidadMapper.toResponseDTO(especialidad);
    }


    @Override
    public EspecialidadResponseDTO postEspecialidad(EspecialidadRequestDTO dto) {
        // Validación de Negocio: No debe existir otra con el mismo nombre
        if (especialidadRepository.existsByNombreEspecialidad(dto.getNombreEspecialidad())) {
            throw new EspecialidadYaExisteException(dto.getNombreEspecialidad(), true);
        }

        Especialidad entity = especialidadMapper.toEntity(dto);
        Especialidad saved = especialidadRepository.save(entity);
        return especialidadMapper.toResponseDTO(saved);
    }


    @Override
    public EspecialidadResponseDTO putEspecialidad(Long idEspecialidad, EspecialidadRequestDTO dto) {
        Especialidad existente = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new EspecialidadNoEncontradaException(idEspecialidad));

        // Validación de Negocio: Si el nombre cambia, debe ser único
        if (!existente.getNombreEspecialidad().equalsIgnoreCase(dto.getNombreEspecialidad())) {
            if (especialidadRepository.existsByNombreEspecialidad(dto.getNombreEspecialidad())) {
                throw new EspecialidadYaExisteException(dto.getNombreEspecialidad(), true);
            }
        }

        existente.setNombreEspecialidad(dto.getNombreEspecialidad());

        Especialidad actualizado = especialidadRepository.save(existente);
        return especialidadMapper.toResponseDTO(actualizado);
    }


    @Override
    public EspecialidadResponseDTO patchEspecialidad(Long idEspecialidad, EspecialidadRequestDTO dto) {
        Especialidad existente = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new EspecialidadNoEncontradaException(idEspecialidad));

        if (dto.getNombreEspecialidad() != null) {
            // Validación de Negocio: Si el nombre cambia, debe ser único
            if (!existente.getNombreEspecialidad().equalsIgnoreCase(dto.getNombreEspecialidad())) {
                if (especialidadRepository.existsByNombreEspecialidad(dto.getNombreEspecialidad())) {
                    throw new EspecialidadYaExisteException(dto.getNombreEspecialidad(), true);
                }
            }
            existente.setNombreEspecialidad(dto.getNombreEspecialidad());
        }

        Especialidad actualizado = especialidadRepository.save(existente);
        return especialidadMapper.toResponseDTO(actualizado);
    }


    @Override
    public void deleteEspecialidad(Long idEspecialidad) {
        if (!especialidadRepository.existsById(idEspecialidad)) {
            throw new EspecialidadNoEncontradaException(idEspecialidad);
        }
        especialidadRepository.deleteById(idEspecialidad);
    }
}
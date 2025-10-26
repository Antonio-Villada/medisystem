package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadResponseDTO;

import java.util.List;

public interface EspecialidadService {

    /**
     * Devuelve todas las especialidades como DTOs.
     */
    List<EspecialidadResponseDTO> getAllEspecialidades();

    /**
     * Busca una especialidad por su ID.
     */
    EspecialidadResponseDTO getEspecialidadById(Long idEspecialidad);

    /**
     * Crea una nueva especialidad.
     */
    EspecialidadResponseDTO postEspecialidad(EspecialidadRequestDTO dto);

    /**
     * Actualiza completamente los datos de una especialidad.
     */
    EspecialidadResponseDTO putEspecialidad(Long idEspecialidad, EspecialidadRequestDTO dto);

    /**
     * Actualiza parcialmente los datos de una especialidad.
     */
    EspecialidadResponseDTO patchEspecialidad(Long idEspecialidad, EspecialidadRequestDTO dto);

    /**
     * Elimina una especialidad por ID.
     */
    void deleteEspecialidad(Long idEspecialidad);
}
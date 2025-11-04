package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadResponseDTO;

import java.util.List;

public interface EspecialidadService {


    List<EspecialidadResponseDTO> getAllEspecialidades();
    EspecialidadResponseDTO getEspecialidadById(Long idEspecialidad);
    EspecialidadResponseDTO postEspecialidad(EspecialidadRequestDTO dto);
    EspecialidadResponseDTO putEspecialidad(Long idEspecialidad, EspecialidadRequestDTO dto);
    EspecialidadResponseDTO patchEspecialidad(Long idEspecialidad, EspecialidadRequestDTO dto);
    void deleteEspecialidad(Long idEspecialidad);
}
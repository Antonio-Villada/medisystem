package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {

    @Mapping(target = "idEspecialidad", ignore = true)
    Especialidad toEntity(EspecialidadRequestDTO dto);

    EspecialidadResponseDTO toResponseDTO(Especialidad entity);
}
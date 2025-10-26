package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {

    // --------------------------------------------------------------------
    // 1. DTO a ENTITY (Creación)
    // --------------------------------------------------------------------

    /**
     * Mapea EspecialidadRequestDTO a Especialidad.
     * El campo 'nombreEspecialidad' se mapea automáticamente.
     * Ignoramos el ID ya que es autogenerado.
     */
    @Mapping(target = "idEspecialidad", ignore = true)
    Especialidad toEntity(EspecialidadRequestDTO dto);


    // --------------------------------------------------------------------
    // 2. ENTITY a RESPONSE DTO (Lectura)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad Especialidad a EspecialidadResponseDTO.
     * Ambos campos (idEspecialidad y nombreEspecialidad) tienen el mismo nombre
     * y se mapean automáticamente.
     */
    EspecialidadResponseDTO toResponseDTO(Especialidad entity);
}
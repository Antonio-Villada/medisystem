package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.eps.EpsRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.eps.EpsResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EpsMapper {

    // --------------------------------------------------------------------
    // 1. DTO a ENTITY (Creación)
    // --------------------------------------------------------------------

    /**
     * Mapea EpsRequestDTO a Eps.
     * El campo 'nombreEps' se mapea automáticamente.
     * Ignoramos el ID ya que es autogenerado.
     */
    @Mapping(target = "idEps", ignore = true)
    Eps toEntity(EpsRequestDTO dto);


    // --------------------------------------------------------------------
    // 2. ENTITY a RESPONSE DTO (Lectura)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad Eps a EpsResponseDTO.
     * Ambos campos (idEps y nombreEps) tienen el mismo nombre y se mapean automáticamente.
     */
    EpsResponseDTO toResponseDTO(Eps entity);
}
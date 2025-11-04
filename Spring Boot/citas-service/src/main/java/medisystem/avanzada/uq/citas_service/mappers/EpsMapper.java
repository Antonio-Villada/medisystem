package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.eps.EpsRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.eps.EpsResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EpsMapper {

    @Mapping(target = "idEps", ignore = true)
    Eps toEntity(EpsRequestDTO dto);

    EpsResponseDTO toResponseDTO(Eps entity);
}
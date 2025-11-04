package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Formula;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DetalleFormulaMapper.class})
public interface FormulaMapper {

    @Mapping(target = "cita", source = "cita")
    @Mapping(target = "idFormula", ignore = true) // El ID es autogenerado
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "fecha", source = "dto.fecha")
    Formula toEntity(FormulaRequestDTO dto, Cita cita);
    @Mapping(target = "idCita", source = "formula.cita.idCita")
    @Mapping(target = "detalles", source = "formula.detalles")
    FormulaResponseDTO toResponseDTO(Formula formula);
}
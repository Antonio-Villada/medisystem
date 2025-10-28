package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Indicamos que use el MedicamentoMapper para mapear el objeto Medicamento
@Mapper(componentModel = "spring", uses = {MedicamentoMapper.class})
public interface DetalleFormulaMapper {
    @Mapping(target = "formula", source = "formula")
    @Mapping(target = "medicamento", source = "medicamento")
    @Mapping(target = "idDetalleFormula", ignore = true)
    DetalleFormula toEntity(DetalleFormulaRequestDTO dto, Formula formula, Medicamento medicamento);

    DetalleFormula toEntity(DetalleFormulaRequestDTO dto);


    @Mapping(target = "medicamento", source = "entity.medicamento")
    DetalleFormulaResponseDTO toResponseDTO(DetalleFormula entity);
}
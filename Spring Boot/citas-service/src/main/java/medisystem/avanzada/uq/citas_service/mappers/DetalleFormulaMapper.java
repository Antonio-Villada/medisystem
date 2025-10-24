package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import org.springframework.stereotype.Component;

@Component
public class DetalleFormulaMapper {

    public DetalleFormula toEntity(DetalleFormulaRequestDTO dto, Formula formula, Medicamento medicamento) {
        DetalleFormula entity = new DetalleFormula();
        entity.setFormula(formula);
        entity.setMedicamento(medicamento);
        entity.setCantidad(dto.getCantidad());
        entity.setDosis(dto.getDosis());
        return entity;
    }

    public DetalleFormulaResponseDTO toResponseDTO(
            DetalleFormula entity,
            FormulaResponseDTO formulaDTO,
            MedicamentoResponseDTO medicamentoDTO) {

        DetalleFormulaResponseDTO dto = new DetalleFormulaResponseDTO();
        dto.setIdDetalleFormula(entity.getIdDetalleFormula());
        dto.setCantidad(entity.getCantidad());
        dto.setDosis(entity.getDosis());
        dto.setFormula(formulaDTO);
        dto.setMedicamento(medicamentoDTO);
        return dto;
    }
}

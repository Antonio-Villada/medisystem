package medisystem.avanzada.uq.citas_service.dtos.detalleFormula;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFormulaResponseDTO {

    private Long idDetalleFormula;
    private Integer cantidad;
    private String dosis;
    private MedicamentoResponseDTO medicamento;


}
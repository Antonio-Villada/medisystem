package medisystem.avanzada.uq.citas_service.dtos.formula;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormulaResponseDTO {

    private Long idFormula;
    private LocalDate fecha;
    private Long idCita;
    private List<DetalleFormulaResponseDTO> detalles;


}
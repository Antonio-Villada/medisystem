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

    private Long idFormula; // CAMBIADO: Integer -> Long

    // CAMBIO 1: Usamos LocalDate para que Jackson lo serialice correctamente
    private LocalDate fecha;

    // CAMBIO 2: Evitamos el bucle infinito. Solo incluimos el ID de la cita.
    private Long idCita;

    // CAMBIO 3: Incluimos el contenido de la fórmula (la lista de medicamentos)
    private List<DetalleFormulaResponseDTO> detalles;

    // ¡Se eliminaron todos los getters, setters y constructores manuales!
}
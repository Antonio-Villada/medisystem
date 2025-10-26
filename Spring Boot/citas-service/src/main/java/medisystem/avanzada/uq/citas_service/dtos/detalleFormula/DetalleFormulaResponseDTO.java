package medisystem.avanzada.uq.citas_service.dtos.detalleFormula;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Incluye @Getter, @Setter, @ToString, etc.
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFormulaResponseDTO {

    private Long idDetalleFormula; // CAMBIADO: Integer -> Long
    private Integer cantidad;
    private String dosis;

    // CAMBIO 1: ELIMINADO FormulaResponseDTO formula; para evitar el bucle infinito.
    // La fórmula contenedora ya lo sabe.

    // Mantenemos el medicamento anidado (lo cual es bueno).
    private MedicamentoResponseDTO medicamento;

    // ¡Se eliminaron todos los getters, setters y constructores manuales!
}
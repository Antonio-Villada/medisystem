package medisystem.avanzada.uq.citas_service.dtos.detalleFormula;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DetalleFormulaRequestDTO {

    @NotNull(message = "El ID del medicamento es obligatorio.")
    private Long idMedicamento; // CAMBIADO: Integer -> Long

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private Integer cantidad;

    @NotBlank(message = "La dosis no puede estar vacía.")
    @Size(max = 255, message = "La dosis es demasiado larga.")
    private String dosis;

}
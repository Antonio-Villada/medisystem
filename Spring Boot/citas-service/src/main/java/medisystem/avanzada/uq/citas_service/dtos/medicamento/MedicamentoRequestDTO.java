package medisystem.avanzada.uq.citas_service.dtos.medicamento;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal; // Importar el tipo de dato correcto

@Data // Incluye @Getter, @Setter, @ToString, etc.
public class MedicamentoRequestDTO {

    @NotBlank(message = "El nombre del medicamento no puede estar vacío.")
    @Size(max = 100, message = "El nombre del medicamento es demasiado largo.")
    private String nombreMedicamento;

    @NotNull(message = "El precio no puede estar vacío.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser positivo.")
    private BigDecimal precio; // CAMBIADO: Integer -> BigDecimal (para seguridad financiera)


}
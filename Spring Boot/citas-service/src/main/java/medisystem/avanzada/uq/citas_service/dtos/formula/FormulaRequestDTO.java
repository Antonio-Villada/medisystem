package medisystem.avanzada.uq.citas_service.dtos.formula;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate; // Usamos el objeto nativo de Java

@Data
public class FormulaRequestDTO {

    @NotNull(message = "La fecha de la fórmula no puede ser nula.")
    // Jackson se encarga de mapear el String JSON "yyyy-MM-dd" a LocalDate.
    private LocalDate fecha;

    @NotNull(message = "Se requiere el ID de la cita asociada.")
    private Long idCita; // CAMBIADO: Integer -> Long


}
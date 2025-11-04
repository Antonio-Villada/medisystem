package medisystem.avanzada.uq.citas_service.dtos.formula;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
@Data
public class FormulaRequestDTO {

    @NotNull(message = "La fecha de la fórmula no puede ser nula.")
    private LocalDate fecha;

    @NotNull(message = "Se requiere el ID de la cita asociada.")
    private Long idCita;


}
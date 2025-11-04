package medisystem.avanzada.uq.citas_service.dtos.eps;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EpsRequestDTO {

    @NotBlank(message = "El nombre de la EPS no puede estar vacío.")
    @Size(max = 100, message = "El nombre de la EPS es demasiado largo.")
    private String nombreEps;

}
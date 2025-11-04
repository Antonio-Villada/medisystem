package medisystem.avanzada.uq.citas_service.dtos.telefono;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoRequestDTO {

    @NotBlank(message = "El número de teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{10}$", message = "El formato del teléfono debe contener solo números (10 dígitos).")
    private String telefono;
}
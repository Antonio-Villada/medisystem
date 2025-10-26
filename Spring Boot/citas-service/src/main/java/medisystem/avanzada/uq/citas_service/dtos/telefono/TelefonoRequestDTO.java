package medisystem.avanzada.uq.citas_service.dtos.telefono;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Incluye @Getter, @Setter, @ToString, etc.
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoRequestDTO {

    @NotBlank(message = "El número de teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{7,20}$", message = "El formato del teléfono debe contener solo números (7 a 20 dígitos).")
    private String telefono; // El número de teléfono
}
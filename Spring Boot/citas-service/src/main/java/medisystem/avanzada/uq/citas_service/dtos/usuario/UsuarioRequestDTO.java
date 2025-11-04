package medisystem.avanzada.uq.citas_service.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 4, message = "El nombre de usuario debe tener al menos 4 caracteres.")
    private String username;
    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;


}
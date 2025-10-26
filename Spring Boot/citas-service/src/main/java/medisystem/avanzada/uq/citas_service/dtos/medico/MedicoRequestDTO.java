package medisystem.avanzada.uq.citas_service.dtos.medico;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MedicoRequestDTO {

    @NotBlank(message = "El nombre del médico no puede estar vacío.")
    @Size(max = 255, message = "El nombre es demasiado largo.")
    private String nombreMedico;

    @NotNull(message = "Debe especificar el ID de la especialidad.")
    private Long idEspecialidad;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Formato de teléfono inválido.")
    private String telefono;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "Debe ser una dirección de correo válida.")
    private String correo;

    // Campos del Usuario a crear/validar:

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 4, message = "El nombre de usuario debe tener al menos 4 caracteres.")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password; // AÑADIDO: Necesario para el registro inicial
}
package medisystem.avanzada.uq.citas_service.dtos.paciente;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;


@Data
public class PacienteRequestDTO {

    @NotBlank(message = "La identificación (idPaciente) no puede estar vacía.")
    @Size(min = 5, max = 20, message = "La identificación debe tener entre 5 y 20 caracteres.")
    private String idPaciente;

    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombrePaciente;

    @NotBlank(message = "La ciudad no puede estar vacía.")
    private String ciudad;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "Debe ser una dirección de correo válida.")
    private String correo;

    @NotNull(message = "El id de la EPS no puede ser nulo.")
    private Long idEps; // CAMBIADO: Integer -> Long (para coincidir con la Entidad)

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 4, message = "El nombre de usuario debe tener al menos 4 caracteres.")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;

    @NotEmpty(message = "Debe proporcionar al menos un número de teléfono.")
    private Set<String> telefonos;


}
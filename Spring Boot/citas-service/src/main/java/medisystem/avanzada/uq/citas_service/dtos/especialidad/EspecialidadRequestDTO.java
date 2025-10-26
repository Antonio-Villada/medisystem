package medisystem.avanzada.uq.citas_service.dtos.especialidad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Incluye @Getter, @Setter, @ToString, etc.
public class EspecialidadRequestDTO {

    @NotBlank(message = "El nombre de la especialidad no puede estar vacío.")
    @Size(max = 100, message = "El nombre de la especialidad es demasiado largo.")
    private String nombreEspecialidad;

    // ¡No se necesitan getters, setters ni constructores manuales!
}
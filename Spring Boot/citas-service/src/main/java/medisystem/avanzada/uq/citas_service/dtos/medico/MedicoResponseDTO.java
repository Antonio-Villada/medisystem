package medisystem.avanzada.uq.citas_service.dtos.medico;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoResponseDTO {

    private Long idMedico;
    private String nombreMedico;
    private String telefono;
    private String correo;
    private String nombreEspecialidad;
    private String username;
    private Set<String> roles;

}
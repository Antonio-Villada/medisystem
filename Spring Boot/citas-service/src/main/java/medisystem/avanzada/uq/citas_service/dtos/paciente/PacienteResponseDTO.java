package medisystem.avanzada.uq.citas_service.dtos.paciente;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponseDTO {
    private String idPaciente;
    private String nombrePaciente;
    private String ciudad;
    private String correo;
    private String nombreEps;
    private String username;
    private Set<String> telefonos;

}
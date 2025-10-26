package medisystem.avanzada.uq.citas_service.dtos.especialidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadResponseDTO {

    private Long idEspecialidad; // Coherente con la entidad (Long)
    private String nombreEspecialidad;


}
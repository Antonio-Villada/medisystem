package medisystem.avanzada.uq.citas_service.dtos.eps;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpsResponseDTO {

    private Long idEps; // Coherente con la entidad Eps (Long)
    private String nombreEps;

}
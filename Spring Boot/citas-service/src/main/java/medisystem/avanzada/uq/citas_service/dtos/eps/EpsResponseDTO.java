package medisystem.avanzada.uq.citas_service.dtos.eps;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpsResponseDTO {

    private Long idEps;
    private String nombreEps;

}
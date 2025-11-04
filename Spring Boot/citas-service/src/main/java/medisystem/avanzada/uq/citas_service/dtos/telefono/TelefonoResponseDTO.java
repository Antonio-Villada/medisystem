package medisystem.avanzada.uq.citas_service.dtos.telefono;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TelefonoResponseDTO {

    private Long idTelefono;
    private String telefono;

}
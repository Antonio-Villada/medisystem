package medisystem.avanzada.uq.citas_service.dtos.telefono;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Incluye @Getter, @Setter, @ToString, etc.
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoResponseDTO {

    private Long idTelefono; // Coherente con la entidad (Long)
    private String telefono; // El número de teléfono


}
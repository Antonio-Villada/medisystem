package medisystem.avanzada.uq.citas_service.dtos.medicamento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoResponseDTO {

    private Long idMedicamento;
    private String nombreMedicamento;
    private BigDecimal precio;


}
package medisystem.avanzada.uq.citas_service.dtos.medicamento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal; // Importar el tipo de dato correcto

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoResponseDTO {

    private Long idMedicamento; // CAMBIADO: Integer -> Long
    private String nombreMedicamento;
    private BigDecimal precio; // CAMBIADO: Integer -> BigDecimal


}
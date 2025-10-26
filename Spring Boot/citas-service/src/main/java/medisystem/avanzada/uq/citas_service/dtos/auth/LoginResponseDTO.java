package medisystem.avanzada.uq.citas_service.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String jwtToken;
    private final String type = "Bearer"; // Estándar de tipo de token

}
package medisystem.avanzada.uq.citas_service.dtos.usuario;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String username;

    // Se mapea el Set<Rol> de la entidad a un Set<String> con los nombres
    private Set<String> roles;

    // ¡Se omite el campo 'password' por seguridad!
}
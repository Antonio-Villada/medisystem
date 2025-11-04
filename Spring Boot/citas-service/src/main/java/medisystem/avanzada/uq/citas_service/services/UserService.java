package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.usuario.UsuarioResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;

import java.util.Optional;

public interface UserService {


    Usuario crearNuevoUsuario(String username, String password, RolNombre rolNombre);
    Optional<Usuario> findByUsername(String username);
    UsuarioResponseDTO getPerfilUsuario(String username);
}
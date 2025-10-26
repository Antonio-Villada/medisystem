package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.usuario.UsuarioResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;

import java.util.Optional;

public interface UserService {

    /**
     * Crea un nuevo usuario en el sistema con una contraseña encriptada y un rol específico.
     * Este es el método central de registro, llamado por MedicoService y PacienteService.
     * * @param username El nombre de usuario (único).
     * @param password La contraseña en texto plano.
     * @param rolNombre El rol a asignar (MEDICO, PACIENTE, ADMINISTRADOR).
     * @return La entidad Usuario recién creada.
     */
    Usuario crearNuevoUsuario(String username, String password, RolNombre rolNombre);

    /**
     * Busca una entidad Usuario por su nombre de usuario.
     * @param username El nombre de usuario.
     * @return Un Optional que contiene la entidad si se encuentra.
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Devuelve el perfil del usuario para el cliente (omitiendo la contraseña).
     * @param username El nombre de usuario.
     * @return El DTO de respuesta con roles y nombre de usuario.
     */
    UsuarioResponseDTO getPerfilUsuario(String username);
}
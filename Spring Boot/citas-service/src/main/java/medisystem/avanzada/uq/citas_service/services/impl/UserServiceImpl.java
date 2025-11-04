package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.usuario.UsuarioResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Rol;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.mappers.UsuarioMapper;
import medisystem.avanzada.uq.citas_service.repositories.RolRepository;
import medisystem.avanzada.uq.citas_service.repositories.UsuarioRepository;
import medisystem.avanzada.uq.citas_service.services.UserService;
import medisystem.avanzada.uq.citas_service.exceptions.UsuarioYaExisteException; // Nueva excepción (409)
import medisystem.avanzada.uq.citas_service.exceptions.UsuarioNoEncontradoException; // Nueva excepción (404)
import medisystem.avanzada.uq.citas_service.exceptions.RolNoEncontradoException; // Nueva excepción (500)

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UserServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                           PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario crearNuevoUsuario(String username, String password, RolNombre rolNombre) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new UsuarioYaExisteException("El nombre de usuario '" + username + "' ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RolNoEncontradoException("Error de configuración: Rol '" + rolNombre.name() + "' no encontrado en la BD."));

        usuario.setRoles(Set.of(rol));
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getPerfilUsuario(String username) {

        Usuario usuario = usuarioRepository.findByUsername(username)

                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con username '" + username + "' no encontrado."));

        return usuarioMapper.toResponseDTO(usuario);
    }
}
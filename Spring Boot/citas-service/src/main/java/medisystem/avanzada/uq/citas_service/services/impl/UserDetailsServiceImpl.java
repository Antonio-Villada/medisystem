package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.Rol;
import medisystem.avanzada.uq.citas_service.repositories.UsuarioRepository;
import medisystem.avanzada.uq.citas_service.exceptions.UsuarioNoEncontradoException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga el objeto Usuario y sus roles (GrantedAuthorities) desde la base de datos
     * y lo envuelve en un objeto UserDetails de Spring Security.
     * @param username El nombre de usuario que intenta iniciar sesión.
     * @return UserDetails que contiene el username, password y roles/permisos.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscar Usuario y Roles
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        // 2. Convertir los Roles a GrantedAuthorities
        // ¡La autoridad ahora coincide con el nombre del ENUM (ej: "ADMINISTRADOR")!
        Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(usuario.getRoles());

        // 3. Devolver un objeto User de Spring Security
        return new User(
                usuario.getUsername(),
                usuario.getPassword(), // Contraseña encriptada
                authorities
        );
    }

    /**
     * Convierte el Set<Rol> de la entidad JPA a la colección de GrantedAuthority de Spring Security.
     * IMPORTANTE: Se ha eliminado el prefijo "ROLE_" ya que la base de datos no lo usa
     * y el SecurityConfig fue ajustado con GrantedAuthorityDefaults("").
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Rol> roles) {
        // Mapea cada Rol a un SimpleGrantedAuthority (ej: "ADMINISTRADOR", "MEDICO")
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre().name())) // 🌟 CORRECCIÓN CLAVE: Quitar "ROLE_" +
                .collect(Collectors.toList());
    }
}

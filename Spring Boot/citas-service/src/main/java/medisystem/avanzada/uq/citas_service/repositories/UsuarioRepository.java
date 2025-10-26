package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Consulta optimizada para la capa de Servicio/Seguridad.
     * Utiliza JOIN FETCH para cargar la entidad Usuario y su colección de Roles
     * en una sola consulta, evitando el problema N+1 y manteniendo la eficiencia
     * del FetchType.LAZY en la entidad.
     */
    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles r WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithRoles(@Param("username") String username);

    // Consulta simple para otros casos que no requieran roles inmediatamente.
    Optional<Usuario> findByUsername(String username);

    // Método eficiente para verificar si un username ya existe.
    boolean existsByUsername(String username);
}
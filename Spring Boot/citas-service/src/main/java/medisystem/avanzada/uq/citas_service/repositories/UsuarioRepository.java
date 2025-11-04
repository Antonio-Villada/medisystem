package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles r WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithRoles(@Param("username") String username);
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}
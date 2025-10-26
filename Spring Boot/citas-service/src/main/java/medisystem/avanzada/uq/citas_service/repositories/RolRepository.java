package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Rol;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Importación necesaria
import java.util.Optional;

@Repository // Mantenemos esta anotación por consistencia de estilo.
public interface RolRepository extends JpaRepository<Rol, Long> {

    // Método para encontrar un rol por su nombre (clave natural), usando Optional.
    Optional<Rol> findByNombre(RolNombre nombre);

    // Método para verificar la existencia (opcional, pero útil para validaciones).
    boolean existsByNombre(RolNombre nombre);
}
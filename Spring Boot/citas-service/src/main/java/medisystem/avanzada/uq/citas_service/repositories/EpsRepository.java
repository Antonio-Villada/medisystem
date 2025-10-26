package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EpsRepository extends JpaRepository<Eps, Long> {

    // Método esencial para buscar una EPS por su nombre (clave natural y única).
    Optional<Eps> findByNombreEps(String nombreEps);

    // Método para validar que no existan nombres duplicados.
    boolean existsByNombreEps(String nombreEps);
}
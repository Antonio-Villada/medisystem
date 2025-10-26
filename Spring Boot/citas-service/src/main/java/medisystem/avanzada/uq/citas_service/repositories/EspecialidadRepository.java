package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    // Método esencial para buscar una especialidad por su nombre (clave natural y única).
    Optional<Especialidad> findByNombreEspecialidad(String nombreEspecialidad);

    // Método para validar que no existan nombres duplicados, optimizado para la existencia.
    boolean existsByNombreEspecialidad(String nombreEspecialidad);
}
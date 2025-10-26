package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Consulta por el username del usuario asociado (original)
    Optional<Medico> findByUsuarioUsername(String username);

    // Búsqueda por la clave natural 'correo'
    Optional<Medico> findByCorreo(String correo);

    // Validación rápida de existencia por correo
    boolean existsByCorreo(String correo);

    // Método para encontrar médicos por su especialidad, útil para citas.
    List<Medico> findByEspecialidad(Especialidad especialidad);

}
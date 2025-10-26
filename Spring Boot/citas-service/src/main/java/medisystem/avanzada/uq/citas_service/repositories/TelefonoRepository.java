package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Long> { // <-- CORREGIDO: Integer -> Long

    // Método esencial para buscar un número de teléfono por su valor String.
    Optional<Telefono> findByTelefono(String telefono);
}
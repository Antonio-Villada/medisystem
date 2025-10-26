package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormulaRepository extends JpaRepository<Formula, Long> {

    // Método para buscar una fórmula asociada a una cita específica.
    Optional<Formula> findByCita(Cita cita);

    // Método para verificar la existencia (útil para lógica de negocio).
    boolean existsByCita(Cita cita);
}
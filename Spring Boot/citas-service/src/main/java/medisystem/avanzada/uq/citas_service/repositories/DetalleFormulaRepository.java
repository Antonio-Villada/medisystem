package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleFormulaRepository extends JpaRepository<DetalleFormula, Integer> {
}

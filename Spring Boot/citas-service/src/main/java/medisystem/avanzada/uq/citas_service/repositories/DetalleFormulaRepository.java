package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleFormulaRepository extends JpaRepository<DetalleFormula, Long> {

    /**
     * Encuentra todos los detalles (medicamentos) asociados a una fórmula específica.
     * Esta es la consulta principal para esta tabla.
     */
    List<DetalleFormula> findByFormula(Formula formula);
}
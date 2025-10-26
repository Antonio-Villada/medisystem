package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    // CORREGIDO: findByNombre -> findByNombreMedicamento
    // Método esencial para buscar medicamentos al crear una fórmula.
    Optional<Medicamento> findByNombreMedicamento(String nombre);

    // CORREGIDO: existsByNombre -> existsByNombreMedicamento
    // Método para validar que no haya nombres de medicamentos duplicados.
    boolean existsByNombreMedicamento(String nombre);
}
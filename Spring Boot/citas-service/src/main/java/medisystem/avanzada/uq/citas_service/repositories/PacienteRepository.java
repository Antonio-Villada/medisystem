package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {
}

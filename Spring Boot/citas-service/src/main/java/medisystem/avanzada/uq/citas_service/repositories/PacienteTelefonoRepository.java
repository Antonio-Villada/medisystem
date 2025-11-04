package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.PacienteTelefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteTelefonoRepository extends JpaRepository<PacienteTelefono, Long> {


    List<PacienteTelefono> findAllByPaciente(Paciente paciente);
    void deleteAllByPaciente(Paciente paciente);
}
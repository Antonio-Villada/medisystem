package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.PacienteTelefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteTelefonoRepository extends JpaRepository<PacienteTelefono, Long> {

    /**
     * Devuelve todos los registros de la tabla de unión
     * que corresponden a un Paciente específico.
     * Esta es la consulta principal que usa el servicio.
     */
    List<PacienteTelefono> findAllByPaciente(Paciente paciente);

    /**
     * Elimina todos los registros de teléfonos para un paciente dado.
     * Útil al actualizar o eliminar un paciente.
     */
    void deleteAllByPaciente(Paciente paciente);
}
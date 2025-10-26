package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> { // <-- CAMBIADO: Integer -> Long

    // Consulta para el límite de citas de un médico por día (original)
    int countByMedicoAndFecha(Medico medico, LocalDate fecha);

    // Búsqueda de disponibilidad precisa (original)
    boolean existsByMedicoAndFechaAndHoraInicio(Medico medico, LocalDate fecha, LocalTime horaInicio);

    // Funcionalidad clave: Obtener citas por paciente.
    List<Cita> findByPaciente(Paciente paciente);

    // Funcionalidad clave: Obtener citas por médico.
    List<Cita> findByMedico(Medico medico);
}
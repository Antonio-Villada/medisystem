package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    int countByMedicoAndFecha(Medico medico, LocalDate fecha);

    // Busca si existe una cita para ese médico en esa fecha y hora
    boolean existsByMedicoAndFechaAndHoraInicio(Medico medico, LocalDate fecha, LocalTime horaInicio);
}

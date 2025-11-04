package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    Optional<Paciente> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    Optional<Paciente> findByUsuarioUsername(String username);
}
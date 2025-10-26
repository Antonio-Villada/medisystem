package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    // Encuentra un Paciente por su correo electrónico (clave única)
    Optional<Paciente> findByCorreo(String correo);

    // Verifica si ya existe un paciente con un correo específico (más eficiente que findByCorreo)
    boolean existsByCorreo(String correo);

    // Encuentra un Paciente a través del username de su Usuario asociado
    Optional<Paciente> findByUsuarioUsername(String username);
}
package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.entities.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoService {

    // ==========================
    // MÉTODOS EXISTENTES
    // ==========================
    List<Medico> getMedicos();
    Medico getMedicoById(Long idMedico);
    Medico postMedico(Medico medico);
    Medico putMedico(Long idMedico, Medico medico);
    void deleteMedico(Long idMedico);
    Medico patchMedico(Long idMedico, Medico medico);

    // ==========================
    // NUEVOS MÉTODOS AGREGADOS
    // ==========================

    Optional<Medico> getMedicoByNombre(String nombreMedico);

    List<Medico> getMedicosByEspecialidad(Long idEspecialidad);

    boolean existsByCorreo(String correo);

    long countMedicos();
}

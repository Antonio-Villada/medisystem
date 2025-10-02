package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.entities.Medico;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicoService {
    List<Medico> getMedicos();
    Medico getMedicoById(Long idMedico);
    Medico postMedico(Medico medico);
    Medico putMedico(Long idMedico, Medico medico);
    void deleteMedico(Long idMedico);
    Medico patchMedico(Long idMedico, Medico medico);
}

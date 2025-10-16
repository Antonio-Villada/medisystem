package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.entities.Medico;

import java.util.List;

public interface MedicoService {
    List<Medico> getMedicos();
    Medico getMedicoById(Long idMedico);
    Medico postMedico(Medico medico);
    Medico putMedico(Long idMedico, Medico medico);
    void deleteMedico(Long idMedico);
    Medico patchMedico(Long idMedico, Medico medico);
}

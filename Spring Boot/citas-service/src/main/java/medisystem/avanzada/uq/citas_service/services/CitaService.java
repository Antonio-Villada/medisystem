package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.entities.Cita;
import java.util.List;

public interface CitaService {
    List<Cita> getCitas();
    Cita getCitaById(Integer idCita);
    Cita postCita(Cita cita);
    Cita putCita(Integer idCita, Cita cita);
    void deleteCita(Integer idCita);
    Cita patchCita(Integer idCita, Cita cita);
}

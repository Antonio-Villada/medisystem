package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import java.util.List;

public interface CitaService {

    // --- Métodos originales que usan la entidad (sin cambios) ---
    Cita putCita(Integer idCita, Cita cita);
    void deleteCita(Integer idCita);
    Cita patchCita(Integer idCita, Cita cita);

    // --- Métodos nuevos que usan DTOs ---
    List<CitaResponseDTO> getCitas();
    CitaResponseDTO getCitaById(Integer idCita);
    CitaResponseDTO postCita(CitaRequestDTO dto);
}

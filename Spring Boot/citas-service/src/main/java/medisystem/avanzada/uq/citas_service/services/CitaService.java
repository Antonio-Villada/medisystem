package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.Medico;

import java.util.List;

public interface CitaService {


    CitaResponseDTO agendarCita(CitaRequestDTO dto);
    List<CitaResponseDTO> getAllCitas();
    CitaResponseDTO getCitaById(Long idCita);
    List<CitaResponseDTO> findByPaciente(Paciente paciente);
    List<CitaResponseDTO> findByMedico(Medico medico);
    CitaResponseDTO updateCita(Long idCita, CitaRequestDTO dto);
    CitaResponseDTO patchCita(Long idCita, CitaRequestDTO dto);
    void deleteCita(Long idCita);
}
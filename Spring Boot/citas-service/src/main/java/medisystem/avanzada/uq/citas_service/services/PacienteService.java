package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import java.util.List;

public interface PacienteService {

    List<PacienteResponseDTO> getAllPacientes();
    PacienteResponseDTO getPacienteById(String idPaciente);
    PacienteResponseDTO registrarPaciente(PacienteRequestDTO dto);
    PacienteResponseDTO putPaciente(String idPaciente, PacienteRequestDTO dto);
    PacienteResponseDTO patchPaciente(String idPaciente, PacienteRequestDTO dto);
    PacienteResponseDTO getPacientePorUsername(String username);
    void deletePaciente(String idPaciente);
}
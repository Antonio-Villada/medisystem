package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import java.util.List;

public interface PacienteService {

    // --- MÉTODOS DE LECTURA (GET) ---
    List<PacienteResponseDTO> getAllPacientes();
    PacienteResponseDTO getPacienteById(String idPaciente);

    // --- MÉTODO DE CREACIÓN (POST, con nombre de negocio) ---
    // Usamos el nombre registrarPaciente para la lógica compleja de creación/usuario.
    PacienteResponseDTO registrarPaciente(PacienteRequestDTO dto);

    // --- MÉTODOS DE ACTUALIZACIÓN (PUT/PATCH) ---
    // Renombramos y tipamos para usar RequestDTOs y devolver ResponseDTOs
    PacienteResponseDTO putPaciente(String idPaciente, PacienteRequestDTO dto);
    PacienteResponseDTO patchPaciente(String idPaciente, PacienteRequestDTO dto);

    // --- MÉTODO DE ELIMINACIÓN (DELETE) ---
    void deletePaciente(String idPaciente);
}
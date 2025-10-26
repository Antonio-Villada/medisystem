package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
// Eliminamos la importación de Especialidad
// import medisystem.avanzada.uq.citas_service.entities.Especialidad;

import java.util.List;

public interface MedicoService {

    // --- MÉTODOS DE REGISTRO Y LECTURA (GET/POST) ---

    /**
     * Registra un nuevo médico en el sistema, creando su usuario asociado.
     * @param dto Los datos de registro, incluyendo usuario y especialidad.
     * @return El DTO de respuesta del médico creado.
     */
    MedicoResponseDTO registrarMedico(MedicoRequestDTO dto);

    /**
     * Devuelve una lista de todos los médicos, transformados a DTOs de respuesta.
     */
    List<MedicoResponseDTO> getAllMedicos();

    /**
     * Busca un médico por su ID (PK) y lo devuelve como DTO.
     */
    MedicoResponseDTO getMedicoById(Long idMedico);

    // --- MÉTODOS DE ACTUALIZACIÓN (PUT/PATCH) ---

    /**
     * Actualiza completamente los datos de un médico existente.
     */
    MedicoResponseDTO updateMedico(Long idMedico, MedicoRequestDTO dto);

    /**
     * Actualiza parcialmente los datos de un médico (requiere lógica de nulidad en la implementación).
     */
    MedicoResponseDTO patchMedico(Long idMedico, MedicoRequestDTO dto);

    // --- MÉTODOS DE ELIMINACIÓN (DELETE) ---

    /**
     * Elimina lógicamente (o físicamente) un médico del sistema.
     */
    void deleteMedico(Long idMedico);

    // --- MÉTODOS DE CONSULTA ESPECÍFICA ---

    /**
     * Busca y devuelve una lista de médicos que pertenecen a una especialidad dada.
     * @param idEspecialidad El ID de la especialidad a buscar.
     */
    List<MedicoResponseDTO> getMedicosByEspecialidadId(Long idEspecialidad); // Ajustado para usar Long
}
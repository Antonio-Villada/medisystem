package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.Medico;

import java.util.List;

public interface CitaService {

    // ==========================================================
    // CREACIÓN / AGENDAMIENTO (POST)
    // ==========================================================
    /**
     * Agenda una nueva cita, aplicando toda la lógica de validación.
     */
    CitaResponseDTO agendarCita(CitaRequestDTO dto);

    // ==========================================================
    // LECTURA (GET)
    // ==========================================================
    /**
     * Devuelve todas las citas como DTOs.
     */
    List<CitaResponseDTO> getAllCitas();

    /**
     * Busca una cita por su ID.
     */
    CitaResponseDTO getCitaById(Long idCita);

    /**
     * Devuelve el historial de citas de un paciente.
     */
    List<CitaResponseDTO> findByPaciente(Paciente paciente);

    /**
     * Devuelve la agenda de citas de un médico.
     */
    List<CitaResponseDTO> findByMedico(Medico medico);

    // ==========================================================
    // ACTUALIZACIÓN Y ELIMINACIÓN (PUT/PATCH/DELETE)
    // ==========================================================
    /**
     * Reemplaza completamente los datos de una cita (PUT).
     */
    CitaResponseDTO updateCita(Long idCita, CitaRequestDTO dto);

    /**
     * Actualiza parcialmente los datos de una cita (PATCH).
     */
    CitaResponseDTO patchCita(Long idCita, CitaRequestDTO dto);

    /**
     * Cancela o elimina una cita.
     */
    void deleteCita(Long idCita);
}
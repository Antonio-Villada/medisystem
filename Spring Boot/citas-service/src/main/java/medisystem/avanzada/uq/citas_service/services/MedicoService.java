package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
// Eliminamos la importación de Especialidad
// import medisystem.avanzada.uq.citas_service.entities.Especialidad;

import java.util.List;

public interface MedicoService {


    MedicoResponseDTO registrarMedico(MedicoRequestDTO dto);
    List<MedicoResponseDTO> getAllMedicos();
    MedicoResponseDTO getMedicoById(Long idMedico);
    MedicoResponseDTO updateMedico(Long idMedico, MedicoRequestDTO dto);
    MedicoResponseDTO patchMedico(Long idMedico, MedicoRequestDTO dto);
    void deleteMedico(Long idMedico);
    List<MedicoResponseDTO> getMedicosByEspecialidadId(Long idEspecialidad); // Ajustado para usar Long
}
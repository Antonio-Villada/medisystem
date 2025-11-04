package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.eps.EpsRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.eps.EpsResponseDTO;

import java.util.List;

public interface EpsService {


    List<EpsResponseDTO> getAllEps();
    EpsResponseDTO getEpsById(Long idEps); // Usamos Long para el ID
    EpsResponseDTO postEps(EpsRequestDTO dto);
    EpsResponseDTO putEps(Long idEps, EpsRequestDTO dto);
    EpsResponseDTO patchEps(Long idEps, EpsRequestDTO dto);
    void deleteEps(Long idEps);
}
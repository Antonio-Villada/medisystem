package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.eps.EpsRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.eps.EpsResponseDTO;

import java.util.List;

public interface EpsService {

    /**
     * Devuelve todas las EPS como DTOs.
     */
    List<EpsResponseDTO> getAllEps();

    /**
     * Busca una EPS por su ID.
     */
    EpsResponseDTO getEpsById(Long idEps); // Usamos Long para el ID

    /**
     * Crea una nueva EPS.
     */
    EpsResponseDTO postEps(EpsRequestDTO dto);

    /**
     * Actualiza completamente los datos de una EPS.
     */
    EpsResponseDTO putEps(Long idEps, EpsRequestDTO dto);

    /**
     * Actualiza parcialmente los datos de una EPS.
     */
    EpsResponseDTO patchEps(Long idEps, EpsRequestDTO dto);

    /**
     * Elimina una EPS por ID.
     */
    void deleteEps(Long idEps);
}
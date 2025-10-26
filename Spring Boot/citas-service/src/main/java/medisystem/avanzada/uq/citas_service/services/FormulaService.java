package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import java.util.List;

public interface FormulaService {

    // --- Métodos originales que trabajan con la entidad (Ajustados a DTOs y Long) ---
    // Usaremos DTOs para actualizar, no entidades.
    FormulaResponseDTO update(Long id, FormulaRequestDTO dto); // CAMBIADO: int -> Long, Formula -> DTO
    void delete(Long id); // CAMBIADO: int -> Long

    // --- Nuevos métodos con DTOs ---
    List<FormulaResponseDTO> findAll();
    FormulaResponseDTO findById(Long id); // CAMBIADO: int -> Long
    FormulaResponseDTO save(FormulaRequestDTO dto);
}
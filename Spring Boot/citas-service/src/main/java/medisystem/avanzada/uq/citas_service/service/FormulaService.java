package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import java.util.List;

public interface FormulaService {

    // --- Métodos originales que trabajan con la entidad ---
    Formula update(int id, Formula formula);
    void delete(int id);

    // --- Nuevos métodos con DTOs ---
    List<FormulaResponseDTO> findAll();
    FormulaResponseDTO findById(int id);
    FormulaResponseDTO save(FormulaRequestDTO dto);
}

package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import java.util.List;

public interface FormulaService {


    FormulaResponseDTO update(Long id, FormulaRequestDTO dto);
    void delete(Long id);
    List<FormulaResponseDTO> findAll();
    FormulaResponseDTO findById(Long id);
    FormulaResponseDTO save(FormulaRequestDTO dto);
}
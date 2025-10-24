package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;

import java.util.List;

public interface DetalleFormulaService {

    List<DetalleFormulaResponseDTO> getDetalleFormulas();
    DetalleFormulaResponseDTO getDetalleFormulaById(Integer idDetalleFormula);
    DetalleFormulaResponseDTO postDetalleFormula(DetalleFormulaRequestDTO detalleFormulaDTO);
    DetalleFormulaResponseDTO putDetalleFormula(Integer idDetalleFormula, DetalleFormulaRequestDTO detalleFormulaDTO);
    void deleteDetalleFormula(Integer idDetalleFormula);
    DetalleFormulaResponseDTO patchDetalleFormula(Integer idDetalleFormula, DetalleFormulaRequestDTO detalleFormulaDTO);
}

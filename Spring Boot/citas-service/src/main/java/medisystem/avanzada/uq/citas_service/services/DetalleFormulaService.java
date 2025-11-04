package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;

import java.util.List;

public interface DetalleFormulaService {

    List<DetalleFormulaResponseDTO> getDetalleFormulas();
    DetalleFormulaResponseDTO getDetalleFormulaById(Long idDetalleFormula);
    DetalleFormulaResponseDTO postDetalleFormula(DetalleFormulaRequestDTO detalleFormulaDTO);
    DetalleFormulaResponseDTO postDetalleFormulaAnidado(Long idFormula, DetalleFormulaRequestDTO detalleFormulaDTO);
    DetalleFormulaResponseDTO putDetalleFormula(Long idDetalleFormula, DetalleFormulaRequestDTO detalleFormulaDTO);
    void deleteDetalleFormula(Long idDetalleFormula);
    DetalleFormulaResponseDTO patchDetalleFormula(Long idDetalleFormula, DetalleFormulaRequestDTO detalleFormulaDTO);
}
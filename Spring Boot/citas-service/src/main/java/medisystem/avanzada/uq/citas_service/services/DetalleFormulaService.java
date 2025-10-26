package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;

import java.util.List;

public interface DetalleFormulaService {

    List<DetalleFormulaResponseDTO> getDetalleFormulas();

    // CAMBIADO: Integer -> Long
    DetalleFormulaResponseDTO getDetalleFormulaById(Long idDetalleFormula);

    DetalleFormulaResponseDTO postDetalleFormula(DetalleFormulaRequestDTO detalleFormulaDTO);

    // CAMBIADO: Integer -> Long
    DetalleFormulaResponseDTO putDetalleFormula(Long idDetalleFormula, DetalleFormulaRequestDTO detalleFormulaDTO);

    // CAMBIADO: Integer -> Long
    void deleteDetalleFormula(Long idDetalleFormula);

    // CAMBIADO: Integer -> Long
    DetalleFormulaResponseDTO patchDetalleFormula(Long idDetalleFormula, DetalleFormulaRequestDTO detalleFormulaDTO);
}
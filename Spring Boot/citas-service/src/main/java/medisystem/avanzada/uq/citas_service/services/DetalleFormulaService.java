package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;

import java.util.List;

public interface DetalleFormulaService {
    List<DetalleFormula> getDetalleFormulas();
    DetalleFormula getDetalleFormulaById(Integer idDetalleFormula);
    DetalleFormula postDetalleFormula(DetalleFormula detalleFormula);
    DetalleFormula putDetalleFormula(Integer idDetalleFormula, DetalleFormula detalleFormula);
    void deleteDetalleFormula(Integer idDetalleFormula);
    DetalleFormula patchDetalleFormula(Integer idDetalleFormula, DetalleFormula detalleFormula);
}

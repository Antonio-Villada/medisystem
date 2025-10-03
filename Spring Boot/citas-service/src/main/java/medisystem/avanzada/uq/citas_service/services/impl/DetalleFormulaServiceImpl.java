package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.exceptions.DetalleFormulaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.DetalleFormulaRepository;
import medisystem.avanzada.uq.citas_service.services.DetalleFormulaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbDetalleFormulaService")
public class DetalleFormulaServiceImpl implements DetalleFormulaService {

    private final DetalleFormulaRepository detalleFormulaRepository;

    public DetalleFormulaServiceImpl(DetalleFormulaRepository detalleFormulaRepository) {
        this.detalleFormulaRepository = detalleFormulaRepository;
    }

    @Override
    public List<DetalleFormula> getDetalleFormulas() {
        return detalleFormulaRepository.findAll();
    }

    @Override
    public DetalleFormula getDetalleFormulaById(Integer idDetalleFormula) {
        return detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException("DetalleFormula con id " + idDetalleFormula + " no encontrada"));
    }

    @Override
    public DetalleFormula postDetalleFormula(DetalleFormula detalleFormula) {
        return detalleFormulaRepository.save(detalleFormula);
    }

    @Override
    public DetalleFormula putDetalleFormula(Integer idDetalleFormula, DetalleFormula detalleFormula) {
        return detalleFormulaRepository.findById(idDetalleFormula)
                .map(df -> {
                    df.setFormula(detalleFormula.getFormula());
                    df.setMedicamento(detalleFormula.getMedicamento());
                    df.setCantidad(detalleFormula.getCantidad());
                    df.setDosis(detalleFormula.getDosis());
                    return detalleFormulaRepository.save(df);
                })
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException("DetalleFormula con id " + idDetalleFormula + " no encontrada"));
    }

    @Override
    public void deleteDetalleFormula(Integer idDetalleFormula) {
        DetalleFormula df = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException("DetalleFormula con id " + idDetalleFormula + " no encontrada"));
        detalleFormulaRepository.delete(df);
    }

    @Override
    public DetalleFormula patchDetalleFormula(Integer idDetalleFormula, DetalleFormula detalleFormula) {
        DetalleFormula existente = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException("DetalleFormula con id " + idDetalleFormula + " no encontrada"));

        if (detalleFormula.getFormula() != null) {
            existente.setFormula(detalleFormula.getFormula());
        }
        if (detalleFormula.getMedicamento() != null) {
            existente.setMedicamento(detalleFormula.getMedicamento());
        }
        if (detalleFormula.getCantidad() != null) {
            existente.setCantidad(detalleFormula.getCantidad());
        }
        if (detalleFormula.getDosis() != null) {
            existente.setDosis(detalleFormula.getDosis());
        }
        return detalleFormulaRepository.save(existente);
    }
}

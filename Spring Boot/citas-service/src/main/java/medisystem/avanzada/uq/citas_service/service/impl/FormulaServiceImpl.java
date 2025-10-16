package medisystem.avanzada.uq.citas_service.service.impl;

import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.exceptions.FormulaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.FormulaRepository;
import medisystem.avanzada.uq.citas_service.service.FormulaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormulaServiceImpl implements FormulaService {

    private final FormulaRepository formulaRepository;

    public FormulaServiceImpl(FormulaRepository formulaRepository) {
        this.formulaRepository = formulaRepository;
    }

    @Override
    public List<Formula> findAll() {
        return formulaRepository.findAll();
    }

    @Override
    public Formula findById(int id) {
        return formulaRepository.findById(id)
                .orElseThrow(() -> new FormulaNoEncontradaException("La fórmula con ID " + id + " no existe"));
    }

    @Override
    public Formula save(Formula formula) {
        return formulaRepository.save(formula);
    }

    @Override
    public Formula update(int id, Formula formula) {
        Formula existente = findById(id);
        existente.setCita(formula.getCita());
        existente.setFecha(formula.getFecha());
        return formulaRepository.save(existente);
    }

    @Override
    public void delete(int id) {
        if (!formulaRepository.existsById(id)) {
            throw new FormulaNoEncontradaException("La fórmula con ID " + id + " no existe");
        }
        formulaRepository.deleteById(id);
    }
}

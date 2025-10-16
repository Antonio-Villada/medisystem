package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.entities.Formula;
import java.util.List;

public interface FormulaService {
    List<Formula> findAll();
    Formula findById(int id);
    Formula save(Formula formula);
    Formula update(int id, Formula formula);
    void delete(int id);
}

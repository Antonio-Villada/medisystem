package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.service.FormulaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formulas")
public class FormulaController {

    private final FormulaService formulaService;

    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }

    @GetMapping
    public List<Formula> getAll() {
        return formulaService.findAll();
    }

    @GetMapping("/{id}")
    public Formula getById(@PathVariable int id) {
        return formulaService.findById(id);
    }

    @PostMapping
    public Formula create(@RequestBody Formula formula) {
        return formulaService.save(formula);
    }

    @PutMapping("/{id}")
    public Formula update(@PathVariable int id, @RequestBody Formula formula) {
        return formulaService.update(id, formula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        formulaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.service.FormulaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/formulas")
public class FormulaController {

    private final FormulaService formulaService;

    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }

    // ==========================================================
    // Métodos con DTOs
    // ==========================================================

    @GetMapping
    public ResponseEntity<List<FormulaResponseDTO>> getAll() {
        return ResponseEntity.ok(formulaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormulaResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(formulaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody FormulaRequestDTO formulaRequest) {
        FormulaResponseDTO nueva = formulaService.save(formulaRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nueva.getIdFormula())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // ==========================================================
    // Métodos originales con la entidad (compatibilidad)
    // ==========================================================

    @PutMapping("/{id}")
    public ResponseEntity<Formula> update(@PathVariable int id, @RequestBody Formula formula) {
        return ResponseEntity.ok(formulaService.update(id, formula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        formulaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.services.DetalleFormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/detalle-formulas")
public class DetalleFormulaController {

    @Autowired
    @Qualifier("dbDetalleFormulaService")
    private DetalleFormulaService detalleFormulaService;

    @GetMapping
    public ResponseEntity<?> getDetalleFormulas() {
        List<DetalleFormula> detalleFormulas = detalleFormulaService.getDetalleFormulas();
        return ResponseEntity.ok(detalleFormulas);
    }

    @GetMapping("/{idDetalleFormula}")
    public ResponseEntity<?> getDetalleFormulaById(@PathVariable Integer idDetalleFormula) {
        DetalleFormula detalleFormula = detalleFormulaService.getDetalleFormulaById(idDetalleFormula);
        return ResponseEntity.ok(detalleFormula);
    }

    @PostMapping
    public ResponseEntity<?> postDetalleFormula(@RequestBody DetalleFormula detalleFormula) {
        DetalleFormula df = detalleFormulaService.postDetalleFormula(detalleFormula);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idDetalleFormula}")
                .buildAndExpand(df.getIdDetalleFormula())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idDetalleFormula}")
    public ResponseEntity<?> putDetalleFormula(@PathVariable Integer idDetalleFormula, @RequestBody DetalleFormula detalleFormula) {
        DetalleFormula actualizado = detalleFormulaService.putDetalleFormula(idDetalleFormula, detalleFormula);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idDetalleFormula}")
    public ResponseEntity<Void> deleteDetalleFormula(@PathVariable Integer idDetalleFormula) {
        detalleFormulaService.deleteDetalleFormula(idDetalleFormula);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idDetalleFormula}")
    public ResponseEntity<DetalleFormula> patchDetalleFormula(@PathVariable Integer idDetalleFormula, @RequestBody DetalleFormula detalleFormula) {
        DetalleFormula actualizado = detalleFormulaService.patchDetalleFormula(idDetalleFormula, detalleFormula);
        return ResponseEntity.ok(actualizado);
    }
}

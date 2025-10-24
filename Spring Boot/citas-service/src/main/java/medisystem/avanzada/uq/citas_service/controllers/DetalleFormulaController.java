package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.service.DetalleFormulaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/detalle-formulas")
public class DetalleFormulaController {

    private final DetalleFormulaService detalleFormulaService;

    public DetalleFormulaController(@Qualifier("dbDetalleFormulaService") DetalleFormulaService detalleFormulaService) {
        this.detalleFormulaService = detalleFormulaService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleFormulaResponseDTO>> getDetalleFormulas() {
        List<DetalleFormulaResponseDTO> detalleFormulas = detalleFormulaService.getDetalleFormulas();
        return ResponseEntity.ok(detalleFormulas);
    }

    @GetMapping("/{idDetalleFormula}")
    public ResponseEntity<DetalleFormulaResponseDTO> getDetalleFormulaById(@PathVariable Integer idDetalleFormula) {
        DetalleFormulaResponseDTO detalleFormula = detalleFormulaService.getDetalleFormulaById(idDetalleFormula);
        return ResponseEntity.ok(detalleFormula);
    }

    @PostMapping
    public ResponseEntity<Void> postDetalleFormula(@RequestBody DetalleFormulaRequestDTO detalleFormulaDTO) {
        DetalleFormulaResponseDTO creado = detalleFormulaService.postDetalleFormula(detalleFormulaDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idDetalleFormula}")
                .buildAndExpand(creado.getIdDetalleFormula())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idDetalleFormula}")
    public ResponseEntity<DetalleFormulaResponseDTO> putDetalleFormula(
            @PathVariable Integer idDetalleFormula,
            @RequestBody DetalleFormulaRequestDTO detalleFormulaDTO) {

        DetalleFormulaResponseDTO actualizado =
                detalleFormulaService.putDetalleFormula(idDetalleFormula, detalleFormulaDTO);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{idDetalleFormula}")
    public ResponseEntity<DetalleFormulaResponseDTO> patchDetalleFormula(
            @PathVariable Integer idDetalleFormula,
            @RequestBody DetalleFormulaRequestDTO detalleFormulaDTO) {

        DetalleFormulaResponseDTO actualizado =
                detalleFormulaService.patchDetalleFormula(idDetalleFormula, detalleFormulaDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idDetalleFormula}")
    public ResponseEntity<Void> deleteDetalleFormula(@PathVariable Integer idDetalleFormula) {
        detalleFormulaService.deleteDetalleFormula(idDetalleFormula);
        return ResponseEntity.noContent().build();
    }
}

package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.services.FormulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/formulas") // Usamos la convención /api/
public class FormulaController {

    private final FormulaService formulaService;

    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<List<FormulaResponseDTO>> findAll() {
        return ResponseEntity.ok(formulaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    // CORREGIDO: int -> Long
    public ResponseEntity<FormulaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(formulaService.findById(id));
    }


    @PostMapping
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<FormulaResponseDTO> create(@Valid @RequestBody FormulaRequestDTO formulaRequest) {
        FormulaResponseDTO nueva = formulaService.save(formulaRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nueva.getIdFormula())
                .toUri();

        // Devolvemos el DTO en el cuerpo (Created 201)
        return ResponseEntity.created(location).body(nueva);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEDICO')")
    // CORREGIDO: int -> Long, Formula -> FormulaRequestDTO
    public ResponseEntity<FormulaResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody FormulaRequestDTO dto) {
        FormulaResponseDTO actualizado = formulaService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    // CORREGIDO: int -> Long
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formulaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
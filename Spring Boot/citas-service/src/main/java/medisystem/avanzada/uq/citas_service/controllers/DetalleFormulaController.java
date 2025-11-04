package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.services.DetalleFormulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/formulas") // Usamos el prefijo principal para la anidación
public class DetalleFormulaController {

    private final DetalleFormulaService detalleFormulaService;

    public DetalleFormulaController(DetalleFormulaService detalleFormulaService) {
        this.detalleFormulaService = detalleFormulaService;
    }

    @PostMapping("/{idFormula}/detalles")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<DetalleFormulaResponseDTO> postDetalleFormula(
            @PathVariable Long idFormula,
            @Valid @RequestBody DetalleFormulaRequestDTO dto) {

        // Usa el nuevo método anidado que maneja la lógica de la fórmula
        DetalleFormulaResponseDTO nuevo = detalleFormulaService.postDetalleFormulaAnidado(idFormula, dto);

        // Construye la URI usando la ruta plana para el nuevo recurso creado
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/detalles/{idDetalleFormula}")
                .buildAndExpand(nuevo.getIdDetalleFormula())
                .toUri();

        return ResponseEntity.created(location).body(nuevo);
    }

    @GetMapping("/detalles")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<List<DetalleFormulaResponseDTO>> getDetalleFormulas() {
        List<DetalleFormulaResponseDTO> detalles = detalleFormulaService.getDetalleFormulas();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/detalles/{idDetalleFormula}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<DetalleFormulaResponseDTO> getDetalleFormulaById(@PathVariable Long idDetalleFormula) {
        DetalleFormulaResponseDTO detalle = detalleFormulaService.getDetalleFormulaById(idDetalleFormula);
        return ResponseEntity.ok(detalle);
    }


    @PutMapping("/detalles/{idDetalleFormula}")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<DetalleFormulaResponseDTO> putDetalleFormula(
            @PathVariable Long idDetalleFormula,
            @Valid @RequestBody DetalleFormulaRequestDTO dto) {

        DetalleFormulaResponseDTO actualizado = detalleFormulaService.putDetalleFormula(idDetalleFormula, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/detalles/{idDetalleFormula}")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<DetalleFormulaResponseDTO> patchDetalleFormula(
            @PathVariable Long idDetalleFormula,
            @RequestBody DetalleFormulaRequestDTO dto) {

        DetalleFormulaResponseDTO actualizado = detalleFormulaService.patchDetalleFormula(idDetalleFormula, dto);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/detalles/{idDetalleFormula}")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<Void> deleteDetalleFormula(@PathVariable Long idDetalleFormula) {
        detalleFormulaService.deleteDetalleFormula(idDetalleFormula);
        return ResponseEntity.noContent().build();
    }
}
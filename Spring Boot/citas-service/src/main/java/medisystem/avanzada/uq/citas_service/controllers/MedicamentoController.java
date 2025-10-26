package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.services.MedicamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicamentos") // Cambiado a /api/medicamentos por convención
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    // ==========================================================
    // GET /api/medicamentos : Listar todos los medicamentos
    // ==========================================================

    /**
     * Permite a Médicos y Administradores consultar el catálogo (necesario para formular).
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<List<MedicamentoResponseDTO>> getMedicamentos() {
        List<MedicamentoResponseDTO> medicamentos = medicamentoService.getMedicamentos();
        return ResponseEntity.ok(medicamentos);
    }

    // ==========================================================
    // GET /api/medicamentos/{idMedicamento} : Obtener medicamento por ID
    // ==========================================================

    /**
     * Permite a Médicos y Administradores consultar un medicamento específico.
     */
    @GetMapping("/{idMedicamento}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    // CORREGIDO: Integer -> Long
    public ResponseEntity<MedicamentoResponseDTO> getMedicamentoById(@PathVariable Long idMedicamento) {
        MedicamentoResponseDTO medicamento = medicamentoService.getMedicamentoById(idMedicamento);
        return ResponseEntity.ok(medicamento);
    }

    // ==========================================================
    // POST /api/medicamentos : Crear nuevo medicamento
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR crear un nuevo medicamento.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<MedicamentoResponseDTO> postMedicamento(@Valid @RequestBody MedicamentoRequestDTO medicamento) {
        MedicamentoResponseDTO nuevo = medicamentoService.postMedicamento(medicamento);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idMedicamento}")
                .buildAndExpand(nuevo.getIdMedicamento())
                .toUri();

        return ResponseEntity.created(location).body(nuevo); // Devuelve el DTO en el cuerpo
    }

    // ==========================================================
    // PUT /api/medicamentos/{idMedicamento} : Actualización total
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR actualizar totalmente un medicamento.
     */
    @PutMapping("/{idMedicamento}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    // CORREGIDO: Integer -> Long
    public ResponseEntity<MedicamentoResponseDTO> putMedicamento(@PathVariable Long idMedicamento,
                                                                 @Valid @RequestBody MedicamentoRequestDTO medicamento) {
        MedicamentoResponseDTO actualizado = medicamentoService.putMedicamento(idMedicamento, medicamento);
        return ResponseEntity.ok(actualizado);
    }

    // ==========================================================
    // DELETE /api/medicamentos/{idMedicamento} : Eliminación
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR eliminar un medicamento.
     */
    @DeleteMapping("/{idMedicamento}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    // CORREGIDO: Integer -> Long
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Long idMedicamento) {
        medicamentoService.deleteMedicamento(idMedicamento);
        return ResponseEntity.noContent().build();
    }

    // ==========================================================
    // PATCH /api/medicamentos/{idMedicamento} : Actualización parcial
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR actualizar parcialmente un medicamento.
     */
    @PatchMapping("/{idMedicamento}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    // CORREGIDO: Integer -> Long
    public ResponseEntity<MedicamentoResponseDTO> patchMedicamento(@PathVariable Long idMedicamento,
                                                                   @RequestBody MedicamentoRequestDTO medicamento) {
        MedicamentoResponseDTO actualizado = medicamentoService.patchMedicamento(idMedicamento, medicamento);
        return ResponseEntity.ok(actualizado);
    }
}
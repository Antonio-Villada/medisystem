package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    @Qualifier("dbMedicamentoService")
    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> getMedicamentos() {
        List<MedicamentoResponseDTO> medicamentos = medicamentoService.getMedicamentos();
        return ResponseEntity.ok(medicamentos);
    }

    @GetMapping("/{idMedicamento}")
    public ResponseEntity<MedicamentoResponseDTO> getMedicamentoById(@PathVariable Integer idMedicamento) {
        MedicamentoResponseDTO medicamento = medicamentoService.getMedicamentoById(idMedicamento);
        return ResponseEntity.ok(medicamento);
    }

    @PostMapping
    public ResponseEntity<Void> postMedicamento(@RequestBody MedicamentoRequestDTO medicamento) {
        MedicamentoResponseDTO nuevo = medicamentoService.postMedicamento(medicamento);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idMedicamento}")
                .buildAndExpand(nuevo.getIdMedicamento())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idMedicamento}")
    public ResponseEntity<MedicamentoResponseDTO> putMedicamento(@PathVariable Integer idMedicamento,
                                                                 @RequestBody MedicamentoRequestDTO medicamento) {
        MedicamentoResponseDTO actualizado = medicamentoService.putMedicamento(idMedicamento, medicamento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idMedicamento}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Integer idMedicamento) {
        medicamentoService.deleteMedicamento(idMedicamento);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idMedicamento}")
    public ResponseEntity<MedicamentoResponseDTO> patchMedicamento(@PathVariable Integer idMedicamento,
                                                                   @RequestBody MedicamentoRequestDTO medicamento) {
        MedicamentoResponseDTO actualizado = medicamentoService.patchMedicamento(idMedicamento, medicamento);
        return ResponseEntity.ok(actualizado);
    }
}

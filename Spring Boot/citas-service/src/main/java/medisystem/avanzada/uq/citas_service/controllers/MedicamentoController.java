package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Medicamento>> getMedicamentos() {
        return ResponseEntity.ok(medicamentoService.getMedicamentos());
    }

    @GetMapping("/{idMedicamento}")
    public ResponseEntity<Medicamento> getMedicamentoById(@PathVariable Integer idMedicamento) {
        return ResponseEntity.ok(medicamentoService.getMedicamentoById(idMedicamento));
    }

    @PostMapping
    public ResponseEntity<Void> postMedicamento(@RequestBody Medicamento medicamento) {
        Medicamento nuevo = medicamentoService.postMedicamento(medicamento);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idMedicamento}")
                .buildAndExpand(nuevo.getIdMedicamento())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idMedicamento}")
    public ResponseEntity<Medicamento> putMedicamento(@PathVariable Integer idMedicamento, @RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(medicamentoService.putMedicamento(idMedicamento, medicamento));
    }

    @DeleteMapping("/{idMedicamento}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Integer idMedicamento) {
        medicamentoService.deleteMedicamento(idMedicamento);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idMedicamento}")
    public ResponseEntity<Medicamento> patchMedicamento(@PathVariable Integer idMedicamento, @RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(medicamentoService.patchMedicamento(idMedicamento, medicamento));
    }
}

package medisystem.avanzada.uq.citas_service.controllers;


import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citaservice/medicamentos")
public class MedicamentoController {


    private MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Medicamento> crearMedicamento(@RequestBody Medicamento medicamento) {
        Medicamento m = medicamentoService.crearMedicamento(medicamento);
        return ResponseEntity.ok(m);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarMedicamento(@PathVariable Long id) {
        medicamentoService.eliminarMedicamento(id);
        return ResponseEntity.ok("Medicamento eliminado correctamente");
    }
}

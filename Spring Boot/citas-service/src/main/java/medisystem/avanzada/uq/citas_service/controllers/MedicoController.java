package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    @Qualifier("dbMedicoService")
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<?> getMedicos() {
        List<Medico> medicos = medicoService.getMedicos();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{idMedico}")
    public ResponseEntity<?> getMedicoById(@PathVariable Long idMedico) {
        Medico medico = medicoService.getMedicoById(idMedico);
        return ResponseEntity.ok(medico);
    }

    @PostMapping
    public ResponseEntity<?> postMedico(@RequestBody Medico medico){
        Medico medico1 = medicoService.postMedico(medico);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idMedico}")
                .buildAndExpand(medico1.getIdMedico())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idMedico}")
    public ResponseEntity<?> putMedico(@PathVariable long idMedico, @RequestBody Medico medico){
        Medico actualizado =medicoService.putMedico(idMedico,medico);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idMedico}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long idMedico) {
        medicoService.deleteMedico(idMedico);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idMedico}")
    public ResponseEntity<Medico> patchMedico(@PathVariable Long idMedico, @RequestBody Medico medico) {
        Medico actualizado = medicoService.patchMedico(idMedico, medico);
        return ResponseEntity.ok(actualizado);
    }


}
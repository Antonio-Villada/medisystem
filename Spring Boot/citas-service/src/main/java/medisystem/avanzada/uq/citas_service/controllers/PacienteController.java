package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    @Qualifier("dbPacienteService")
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> getPacientes() {
        return ResponseEntity.ok(pacienteService.getPacientes());
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable String idPaciente) {
        return ResponseEntity.ok(pacienteService.getPacienteById(idPaciente));
    }

    @PostMapping
    public ResponseEntity<Void> postPaciente(@RequestBody Paciente paciente) {
        Paciente nuevo = pacienteService.postPaciente(paciente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idPaciente}")
                .buildAndExpand(nuevo.getIdPaciente())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idPaciente}")
    public ResponseEntity<Paciente> putPaciente(@PathVariable String idPaciente,
                                                @RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.putPaciente(idPaciente, paciente));
    }

    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<Void> deletePaciente(@PathVariable String idPaciente) {
        pacienteService.deletePaciente(idPaciente);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idPaciente}")
    public ResponseEntity<Paciente> patchPaciente(@PathVariable String idPaciente,
                                                  @RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.patchPaciente(idPaciente, paciente));
    }
}

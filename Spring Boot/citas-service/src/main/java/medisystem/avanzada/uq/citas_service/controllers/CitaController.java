package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.service.CitaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Qualifier("dbCitaService")
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // ==========================================================
    // Métodos con DTOs
    // ==========================================================

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> getCitas() {
        return ResponseEntity.ok(citaService.getCitas());
    }

    @GetMapping("/{idCita}")
    public ResponseEntity<CitaResponseDTO> getCitaById(@PathVariable Integer idCita) {
        return ResponseEntity.ok(citaService.getCitaById(idCita));
    }

    @PostMapping
    public ResponseEntity<Void> postCita(@RequestBody CitaRequestDTO citaRequest) {
        CitaResponseDTO nueva = citaService.postCita(citaRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idCita}")
                .buildAndExpand(nueva.getIdCita())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // ==========================================================
    // Métodos originales (mantienen entidades)
    // ==========================================================

    @PutMapping("/{idCita}")
    public ResponseEntity<Cita> putCita(@PathVariable Integer idCita, @RequestBody Cita cita) {
        return ResponseEntity.ok(citaService.putCita(idCita, cita));
    }

    @DeleteMapping("/{idCita}")
    public ResponseEntity<Void> deleteCita(@PathVariable Integer idCita) {
        citaService.deleteCita(idCita);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idCita}")
    public ResponseEntity<Cita> patchCita(@PathVariable Integer idCita, @RequestBody Cita cita) {
        return ResponseEntity.ok(citaService.patchCita(idCita, cita));
    }
}

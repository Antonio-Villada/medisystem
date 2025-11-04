package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.services.CitaService;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }


    @PostMapping
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<CitaResponseDTO> agendarCita(@Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO nuevaCita = citaService.agendarCita(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<List<CitaResponseDTO>> getAllCitas() {
        // La lógica de filtrado por rol se maneja en el CitaServiceImpl.
        List<CitaResponseDTO> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<CitaResponseDTO> getCitaById(@PathVariable Long id) {
        CitaResponseDTO cita = citaService.getCitaById(id);
        return ResponseEntity.ok(cita);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<CitaResponseDTO> updateCita(@PathVariable Long id,
                                                      @Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO updatedCita = citaService.updateCita(id, dto);
        return ResponseEntity.ok(updatedCita);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<CitaResponseDTO> patchCita(@PathVariable Long id,
                                                     @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO updatedCita = citaService.patchCita(id, dto);
        return ResponseEntity.ok(updatedCita);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}
package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pacientes") // Cambiado a /api/pacientes por convención
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')") // Generalmente, el registro inicial está abierto o es para el admin.
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(@Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO response = pacienteService.registrarPaciente(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idPaciente}")
                .buildAndExpand(response.getIdPaciente())
                .toUri();

        return ResponseEntity.created(location).body(response); // Devuelve el DTO de respuesta
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<List<PacienteResponseDTO>> getAllPacientes() {
        List<PacienteResponseDTO> pacientes = pacienteService.getAllPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable String idPaciente) {
        PacienteResponseDTO paciente = pacienteService.getPacienteById(idPaciente);
        return ResponseEntity.ok(paciente);
    }


    @PutMapping("/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PACIENTE')")
    public ResponseEntity<PacienteResponseDTO> putPaciente(@PathVariable String idPaciente,
                                                           @Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO actualizado = pacienteService.putPaciente(idPaciente, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PACIENTE')")
    public ResponseEntity<PacienteResponseDTO> patchPaciente(@PathVariable String idPaciente,
                                                             @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO actualizado = pacienteService.patchPaciente(idPaciente, dto);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/{idPaciente}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deletePaciente(@PathVariable String idPaciente) {
        pacienteService.deletePaciente(idPaciente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<PacienteResponseDTO> getPacientePropio() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        PacienteResponseDTO paciente = pacienteService.getPacientePorUsername(username);
        return ResponseEntity.ok(paciente);
    }
}
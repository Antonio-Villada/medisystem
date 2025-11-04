package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.eps.EpsRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.eps.EpsResponseDTO;
import medisystem.avanzada.uq.citas_service.services.EpsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eps") // Cambiado a /api/eps por convención
public class EpsController {

    private final EpsService epsService;

    public EpsController(EpsService epsService) {
        this.epsService = epsService;
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<List<EpsResponseDTO>> getAllEps() {
        List<EpsResponseDTO> eps = epsService.getAllEps();
        return ResponseEntity.ok(eps);
    }

    @GetMapping("/{idEps}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    // CORREGIDO: int -> Long
    public ResponseEntity<EpsResponseDTO> getEpsById(@PathVariable Long idEps) {
        EpsResponseDTO eps = epsService.getEpsById(idEps);
        return ResponseEntity.ok(eps);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    // CORREGIDO: Eps -> EpsRequestDTO y devuelve EpsResponseDTO
    public ResponseEntity<EpsResponseDTO> postEps(@Valid @RequestBody EpsRequestDTO dto){
        EpsResponseDTO nuevaEps = epsService.postEps(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idEps}")
                .buildAndExpand(nuevaEps.getIdEps())
                .toUri();

        return ResponseEntity.created(location).body(nuevaEps);
    }


    @PutMapping("/{idEps}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    // CORREGIDO: int -> Long, Eps -> EpsRequestDTO y devuelve EpsResponseDTO
    public ResponseEntity<EpsResponseDTO> putEps(@PathVariable Long idEps,
                                                 @Valid @RequestBody EpsRequestDTO dto){
        EpsResponseDTO actualizado = epsService.putEps(idEps, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idEps}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    // CORREGIDO: int -> Long
    public ResponseEntity<Void> deleteEps(@PathVariable Long idEps) {
        epsService.deleteEps(idEps);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{idEps}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    // CORREGIDO: int -> Long, Eps -> EpsRequestDTO y devuelve EpsResponseDTO
    public ResponseEntity<EpsResponseDTO> patchEps(@PathVariable Long idEps,
                                                   @RequestBody EpsRequestDTO dto) {
        EpsResponseDTO actualizado = epsService.patchEps(idEps, dto);
        return ResponseEntity.ok(actualizado);
    }
}
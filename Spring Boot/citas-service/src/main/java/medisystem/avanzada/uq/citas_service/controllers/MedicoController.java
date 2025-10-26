package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.services.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos") // Cambiado a /api/medicos por convención
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    // ==========================================================
    // POST /api/medicos : Registrar nuevo médico
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR registrar un nuevo médico.
     * Utiliza el método registrarMedico que crea el Usuario y la Entidad.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<MedicoResponseDTO> registrarMedico(@Valid @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO nuevoMedico = medicoService.registrarMedico(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idMedico}")
                .buildAndExpand(nuevoMedico.getIdMedico())
                .toUri();

        return ResponseEntity.created(location).body(nuevoMedico); // Devuelve el DTO en el cuerpo
    }

    // ==========================================================
    // GET /api/medicos : Listar todos los médicos
    // ==========================================================

    /**
     * Permite a todos los roles ver la lista de médicos (esencial para agendar citas).
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<List<MedicoResponseDTO>> getAllMedicos() {
        List<MedicoResponseDTO> medicos = medicoService.getAllMedicos();
        return ResponseEntity.ok(medicos);
    }

    // ==========================================================
    // GET /api/medicos/{idMedico} : Obtener médico por ID
    // ==========================================================

    /**
     * Permite a todos consultar un médico específico.
     */
    @GetMapping("/{idMedico}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<MedicoResponseDTO> getMedicoById(@PathVariable Long idMedico) {
        MedicoResponseDTO medico = medicoService.getMedicoById(idMedico);
        return ResponseEntity.ok(medico);
    }

    // ==========================================================
    // PUT /api/medicos/{idMedico} : Actualización total
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR o al MEDICO que se está editando actualizar totalmente.
     */
    @PutMapping("/{idMedico}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')") // La lógica de 'solo el propio médico' debe ir en el servicio
    public ResponseEntity<MedicoResponseDTO> updateMedico(@PathVariable Long idMedico,
                                                          @Valid @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO actualizado = medicoService.updateMedico(idMedico, dto);
        return ResponseEntity.ok(actualizado);
    }

    // ==========================================================
    // DELETE /api/medicos/{idMedico} : Eliminación
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR eliminar un registro.
     */
    @DeleteMapping("/{idMedico}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long idMedico) {
        medicoService.deleteMedico(idMedico);
        return ResponseEntity.noContent().build();
    }

    // ==========================================================
    // PATCH /api/medicos/{idMedico} : Actualización parcial
    // ==========================================================

    /**
     * Permite al ADMINISTRADOR o al MEDICO asociado actualizar parcialmente.
     */
    @PatchMapping("/{idMedico}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<MedicoResponseDTO> patchMedico(@PathVariable Long idMedico,
                                                         @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO actualizado = medicoService.patchMedico(idMedico, dto);
        return ResponseEntity.ok(actualizado);
    }

    // ==========================================================
    // GET /api/medicos/especialidad/{idEspecialidad} : Búsqueda por filtro
    // ==========================================================

    /**
     * Permite a todos ver los médicos filtrados por especialidad.
     */
    @GetMapping("/especialidad/{idEspecialidad}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<List<MedicoResponseDTO>> getMedicosByEspecialidad(@PathVariable Long idEspecialidad) {
        List<MedicoResponseDTO> medicos = medicoService.getMedicosByEspecialidadId(idEspecialidad);
        return ResponseEntity.ok(medicos);
    }
}
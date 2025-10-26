package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadResponseDTO;
import medisystem.avanzada.uq.citas_service.services.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/especialidades") // Cambiado a /api/especialidades por convención
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    // Inyección por constructor
    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    // ==========================================================
    // GET /api/especialidades : Listar todas las Especialidades
    // ==========================================================

    /**
     * Permite a todos los roles consultar las especialidades (necesario para registro de Médicos).
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<List<EspecialidadResponseDTO>> getAllEspecialidades(){
        List<EspecialidadResponseDTO> especialidades = especialidadService.getAllEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    // ==========================================================
    // GET /api/especialidades/{idEspecialidad} : Obtener por ID
    // ==========================================================

    /**
     * Permite a todos consultar una especialidad específica.
     */
    @GetMapping("/{idEspecialidad}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<EspecialidadResponseDTO> getEspecialidadById(@PathVariable Long idEspecialidad){
        EspecialidadResponseDTO especialidad = especialidadService.getEspecialidadById(idEspecialidad);
        return ResponseEntity.ok(especialidad);
    }

    // ==========================================================
    // POST /api/especialidades : Crear nueva Especialidad
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR crear una nueva especialidad.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<EspecialidadResponseDTO> postEspecialidad(@Valid @RequestBody EspecialidadRequestDTO dto){
        EspecialidadResponseDTO nuevaEspecialidad = especialidadService.postEspecialidad(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idEspecialidad}")
                .buildAndExpand(nuevaEspecialidad.getIdEspecialidad())
                .toUri();

        return ResponseEntity.created(location).body(nuevaEspecialidad);
    }

    // ==========================================================
    // PUT /api/especialidades/{idEspecialidad} : Actualización total
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR actualizar totalmente una especialidad.
     */
    @PutMapping("/{idEspecialidad}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<EspecialidadResponseDTO> putEspecialidad(@PathVariable Long idEspecialidad,
                                                                   @Valid @RequestBody EspecialidadRequestDTO dto){
        EspecialidadResponseDTO actualizado = especialidadService.putEspecialidad(idEspecialidad, dto);
        return ResponseEntity.ok(actualizado);
    }

    // ==========================================================
    // DELETE /api/especialidades/{idEspecialidad} : Eliminación
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR eliminar una especialidad.
     */
    @DeleteMapping("/{idEspecialidad}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable Long idEspecialidad){
        especialidadService.deleteEspecialidad(idEspecialidad);
        return ResponseEntity.noContent().build();
    }

    // ==========================================================
    // PATCH /api/especialidades/{idEspecialidad} : Actualización parcial
    // ==========================================================

    /**
     * Permite solo al ADMINISTRADOR actualizar parcialmente una especialidad.
     */
    @PatchMapping("/{idEspecialidad}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<EspecialidadResponseDTO> patchEspecialidad(@PathVariable Long idEspecialidad,
                                                                     @RequestBody EspecialidadRequestDTO dto){
        EspecialidadResponseDTO actualizado = especialidadService.patchEspecialidad(idEspecialidad, dto);
        return ResponseEntity.ok(actualizado);
    }
}
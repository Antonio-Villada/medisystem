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

    // ==========================================================
    // POST /api/citas : Agendar nueva cita
    // ==========================================================

    /**
     * Permite a un PACIENTE agendar una cita.
     * @param dto Contiene fecha, hora, idMedico e idPaciente.
     */
    @PostMapping
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<CitaResponseDTO> agendarCita(@Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO nuevaCita = citaService.agendarCita(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    // ==========================================================
    // GET /api/citas : Obtener todas las citas (ADMIN)
    // ==========================================================

    /**
     * Permite a ADMIN ver todas las citas. PACIENTE/MEDICO ven las suyas (lógica en servicio/security).
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<List<CitaResponseDTO>> getAllCitas() {
        // La lógica de filtrado por rol se maneja en el CitaServiceImpl.
        List<CitaResponseDTO> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    // ==========================================================
    // GET /api/citas/{id} : Obtener una cita por ID
    // ==========================================================

    /**
     * Permite a ADMIN, el PACIENTE o el MEDICO asociado ver la cita.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<CitaResponseDTO> getCitaById(@PathVariable Long id) {
        CitaResponseDTO cita = citaService.getCitaById(id);
        return ResponseEntity.ok(cita);
    }

    // ==========================================================
    // GET /api/citas/paciente/{idPaciente} : Historial de citas por paciente
    // NOTA: Requiere que el método en el servicio reciba la Entidad Paciente
    // o que se cambie la firma del método en el servicio.
    // ==========================================================
    
    /* * NOTA: Este método está comentado porque la implementación del servicio 
     * lo requiere con la Entidad Paciente, lo cual no es ideal en el Controller.
     * Si usas la versión con ID, debes cambiar la interfaz CitaService.
    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PACIENTE')")
    public ResponseEntity<List<CitaResponseDTO>> getCitasByPaciente(@PathVariable String idPaciente) {
        // En un Controller, deberías buscar la Entidad Paciente aquí si el servicio lo requiere, o
        // cambiar el servicio para que acepte String idPaciente.
        // Asumiendo que el servicio fue corregido para usar ID, se usaría:
        // List<CitaResponseDTO> citas = citaService.getCitasByPacienteId(idPaciente); 
        // return ResponseEntity.ok(citas);
    }
    */


    // ==========================================================
    // PUT /api/citas/{id} : Actualización total de la cita
    // ==========================================================

    /**
     * Permite a ADMIN o el MEDICO asociado actualizar los datos de la cita.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<CitaResponseDTO> updateCita(@PathVariable Long id,
                                                      @Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO updatedCita = citaService.updateCita(id, dto);
        return ResponseEntity.ok(updatedCita);
    }

    // ==========================================================
    // PATCH /api/citas/{id} : Actualización parcial de la cita
    // ==========================================================

    /**
     * Permite a ADMIN o el MEDICO asociado actualizar parcialmente los datos.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO')")
    public ResponseEntity<CitaResponseDTO> patchCita(@PathVariable Long id,
                                                     @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO updatedCita = citaService.patchCita(id, dto);
        return ResponseEntity.ok(updatedCita);
    }

    // ==========================================================
    // DELETE /api/citas/{id} : Cancelar cita
    // ==========================================================

    /**
     * Permite a ADMIN, el MEDICO o el PACIENTE asociado cancelar la cita.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}
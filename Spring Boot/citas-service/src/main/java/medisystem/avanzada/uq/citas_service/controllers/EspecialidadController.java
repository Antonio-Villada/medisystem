package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/Especialidad")
public class EspecialidadController {



    @Qualifier("dbEspecialidadService")
    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public ResponseEntity<?> getEspecialidad(){
        List<Especialidad> especialidades = especialidadService.getEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{idEspecialidad}")
    public ResponseEntity<?>getEspecialidadById(@PathVariable Long idEspecialidad){
        Especialidad especialidad = especialidadService.getEspecialidadByid(idEspecialidad);
        return ResponseEntity.ok(especialidad);
    }

    @PostMapping
    public ResponseEntity<?> postEspecialidad(@RequestBody Especialidad especialidad){
        Especialidad especialidad1 = especialidadService.postEspecialidad(especialidad);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idEspecialidad}")
                .buildAndExpand(especialidad1.getIdEspecialidad())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idEspecialidad}")
    public ResponseEntity<?> putEspecialidad(@PathVariable Long idEspecialidad,@RequestBody Especialidad especialidad){
        Especialidad actualizado = especialidadService.putEspecialidad(idEspecialidad,especialidad);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idEspecialidad}")
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable Long idEspecialidad){
        especialidadService.deleteEspecialidad(idEspecialidad);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idEspecialidad}")
    public ResponseEntity<Especialidad> patchEspecialidad(@PathVariable Long idEspecialidad,@RequestBody Especialidad especialidad){
        Especialidad actualizado = especialidadService.pachEspecialidad(idEspecialidad ,especialidad);
        return ResponseEntity.ok(actualizado);
    }

}

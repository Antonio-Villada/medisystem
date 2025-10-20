package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Telefono;
import medisystem.avanzada.uq.citas_service.service.TelefonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/telefonos")
public class TelefonoController {


    @Qualifier("dbTelefonoService")
    private final TelefonoService telefonoService;

    public TelefonoController(TelefonoService telefonoService) {
        this.telefonoService = telefonoService;
    }

    @GetMapping
    public ResponseEntity<List<Telefono>> getTelefonos() {
        return ResponseEntity.ok(telefonoService.getTelefonos());
    }

    @GetMapping("/{idTelefono}")
    public ResponseEntity<Telefono> getTelefonoById(@PathVariable Integer idTelefono) {
        return ResponseEntity.ok(telefonoService.getTelefonoById(idTelefono));
    }

    @PostMapping
    public ResponseEntity<Void> postTelefono(@RequestBody Telefono telefono) {
        Telefono nuevo = telefonoService.postTelefono(telefono);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idTelefono}")
                .buildAndExpand(nuevo.getIdTelefono())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idTelefono}")
    public ResponseEntity<Telefono> putTelefono(@PathVariable Integer idTelefono,
                                                @RequestBody Telefono telefono) {
        return ResponseEntity.ok(telefonoService.putTelefono(idTelefono, telefono));
    }

    @DeleteMapping("/{idTelefono}")
    public ResponseEntity<Void> deleteTelefono(@PathVariable Integer idTelefono) {
        telefonoService.deleteTelefono(idTelefono);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idTelefono}")
    public ResponseEntity<Telefono> patchTelefono(@PathVariable Integer idTelefono,
                                                  @RequestBody Telefono telefono) {
        return ResponseEntity.ok(telefonoService.patchTelefono(idTelefono, telefono));
    }
}

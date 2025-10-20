package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.service.EpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eps")
public class EpsController {


    @Qualifier("dbEpsService")
    private final EpsService epsService;

    public EpsController(EpsService epsService) {
        this.epsService = epsService;
    }

    @GetMapping
    public ResponseEntity<?> getEps() {
        List<Eps> eps = epsService.getEps();
        return ResponseEntity.ok(eps);
    }

    @GetMapping("/{idEps}")
    public ResponseEntity<?> getEpsById(@PathVariable int idEps) {
        Eps eps = epsService.getEpsById(idEps);
        return ResponseEntity.ok(eps);
    }

    @PostMapping
    public ResponseEntity<?> postEps(@RequestBody Eps eps){
        Eps eps1 = epsService.postEps(eps);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idEps}")
                .buildAndExpand(eps1.getIdEps())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idEps}")
    public ResponseEntity<?> putEps(@PathVariable int idEps, @RequestBody Eps eps){
        Eps actualizado =epsService.putEps(idEps,eps);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idEps}")
    public ResponseEntity<Void> deleteEps(@PathVariable int idEps) {
        epsService.deleteEps(idEps);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idEps}")
    public ResponseEntity<Eps> patchEps(@PathVariable int idEps, @RequestBody Eps eps) {
        Eps actualizado = epsService.patchEps(idEps, eps);
        return ResponseEntity.ok(actualizado);
    }


}
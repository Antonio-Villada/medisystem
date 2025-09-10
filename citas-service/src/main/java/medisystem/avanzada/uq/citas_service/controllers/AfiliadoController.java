package medisystem.avanzada.uq.citas_service.controllers;

import medisystem.avanzada.uq.citas_service.entities.Afiliado;
import medisystem.avanzada.uq.citas_service.services.AfiliadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/afiliados")
public class AfiliadoController {

    private final AfiliadoService afiliadoService;

    public AfiliadoController(AfiliadoService afiliadoService) {
        this.afiliadoService = afiliadoService;
    }

    @GetMapping
    public List<Afiliado> listarAfiliados() {
        return afiliadoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Afiliado> obtenerAfiliado(@PathVariable Long id) {
        return afiliadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Afiliado crearAfiliado(@RequestBody Afiliado afiliado) {
        return afiliadoService.guardar(afiliado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Afiliado> actualizarAfiliado(@PathVariable Long id, @RequestBody Afiliado afiliado) {
        return afiliadoService.buscarPorId(id)
                .map(a -> {
                    afiliado.setAfiliadoId(id);
                    return ResponseEntity.ok(afiliadoService.guardar(afiliado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAfiliado(@PathVariable Long id) {
        return afiliadoService.buscarPorId(id)
                .map(a -> {
                    afiliadoService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}

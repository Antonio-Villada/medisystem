package uq.avanzada.taller4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uq.avanzada.taller4.entities.Producto;
import uq.avanzada.taller4.repositories.ProductoRepository;
import uq.avanzada.taller4.services.ProductoService;

import java.util.List;

@RestController
@RequestMapping({"/productos","/Productos", "producto", "/Producto"})
public class ProductoController {


    @Autowired
    @Qualifier("dbProductoService")
    private ProductoService productoService;

    @GetMapping
    private ResponseEntity<List<Producto>> getProductos(){

        return ResponseEntity.ok(productoService.getProductos());
    }

}

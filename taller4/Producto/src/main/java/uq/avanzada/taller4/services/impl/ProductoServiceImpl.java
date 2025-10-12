package uq.avanzada.taller4.services.impl;

import org.springframework.stereotype.Service;
import uq.avanzada.taller4.entities.Producto;
import uq.avanzada.taller4.repositories.ProductoRepository;
import uq.avanzada.taller4.services.ProductoService;

import java.util.List;

@Service("dbProductoService")
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }
}

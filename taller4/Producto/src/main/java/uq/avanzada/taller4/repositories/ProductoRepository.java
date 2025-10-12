package uq.avanzada.taller4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uq.avanzada.taller4.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

package medisystem.avanzada.uq.citas_service.repositories;

import medisystem.avanzada.uq.citas_service.entities.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpsRepository extends JpaRepository<Eps, Integer> {
}

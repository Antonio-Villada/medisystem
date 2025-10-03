package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.entities.Eps;

import java.util.List;

public interface EpsService {
    List<Eps> getEps();
    Eps getEpsById(int idEps);
    Eps postEps(Eps eps);
    Eps putEps(int idEps, Eps eps);
    void deleteEps(int idEps);
    Eps patchEps(int idEps, Eps eps);
}

package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.exceptions.EpsNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.EpsRepository;
import medisystem.avanzada.uq.citas_service.services.EpsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbEpsService")
public class EpsServiceImpl implements EpsService {

    private final EpsRepository epsRepository;

    public EpsServiceImpl(EpsRepository epsRepository) {
        this.epsRepository = epsRepository;
    }


    @Override
    public List<Eps> getEps() {
        return epsRepository.findAll();
    }

    @Override
    public Eps getEpsById(int idEps) {
        return epsRepository.findById(idEps)
                .orElseThrow(()->new EpsNoEncontradaException(
                "Eps con id "+ idEps+ " no encontarda"));
    }

    @Override
    public Eps postEps(Eps eps) {
        return epsRepository.save(eps);
    }

    @Override
    public Eps putEps(int idEps, Eps eps) {
        return epsRepository.findById(idEps)
                .map(m-> {
                    m.setNombreEps(eps.getNombreEps());
                    return epsRepository.save(m);
                })
                .orElseThrow(()->new EpsNoEncontradaException
                        ("Eps con id " +idEps+ " no encontrada"
                ));
    }

    @Override
    public void deleteEps(int idEps) {
        Eps eps1 = epsRepository.findById(idEps).orElseThrow(()->new EpsNoEncontradaException
                ("Eps con id " +idEps+ " no encontrada"
                ));
        epsRepository.delete(eps1);
    }

    @Override
    public Eps patchEps(int idEps, Eps eps) {
        Eps existente = epsRepository.findById(idEps).orElseThrow(()->new EpsNoEncontradaException
                ("Eps con id " +idEps+ " no encontrada"
                ));
        if (eps.getNombreEps() != null){
            existente.setNombreEps(eps.getNombreEps());
        }
        return epsRepository.save(existente);
    }


}

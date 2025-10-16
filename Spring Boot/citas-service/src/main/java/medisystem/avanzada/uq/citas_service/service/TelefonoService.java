package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.entities.Telefono;
import java.util.List;

public interface TelefonoService {
    List<Telefono> getTelefonos();
    Telefono getTelefonoById(Integer idTelefono);
    Telefono postTelefono(Telefono telefono);
    Telefono putTelefono(Integer idTelefono, Telefono telefono);
    void deleteTelefono(Integer idTelefono);
    Telefono patchTelefono(Integer idTelefono, Telefono telefono);
}

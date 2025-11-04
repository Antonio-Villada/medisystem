package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicoYaExisteException extends RuntimeException {

    public MedicoYaExisteException(String id) {
        super("El Médico con ID/Identificación " + id + " ya existe en el sistema.");
    }

    public MedicoYaExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public MedicoYaExisteException(String correo, boolean esCorreo) {
        super("El Médico con correo " + correo + " ya existe en el sistema.");
    }

    public MedicoYaExisteException(Long id, boolean porId) {
        super("El Médico con ID " + id + " ya existe en el sistema.");
    }
}
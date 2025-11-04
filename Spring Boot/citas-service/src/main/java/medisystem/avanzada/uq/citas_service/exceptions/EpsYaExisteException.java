package medisystem.avanzada.uq.citas_service.exceptions;

public final class EpsYaExisteException extends RuntimeException {

    public EpsYaExisteException(String message) {
        super(message);
    }

    public EpsYaExisteException(String nombre, boolean porNombre) {
        super("La EPS '" + nombre + "' ya está registrada en el sistema.");
    }

    public EpsYaExisteException(Long id) {
        super("La EPS con ID " + id + " ya existe en el sistema.");
    }
}
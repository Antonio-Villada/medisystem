package medisystem.avanzada.uq.citas_service.exceptions;

public final class EspecialidadYaExisteException extends RuntimeException {

    public EspecialidadYaExisteException(String message) {
        super(message);
    }

    public EspecialidadYaExisteException(String nombre, boolean porNombre) {
        super("La Especialidad '" + nombre + "' ya está registrada en el sistema.");
    }

    public EspecialidadYaExisteException(Long id) {
        super("La Especialidad con ID " + id + " ya existe en el sistema.");
    }
}
package medisystem.avanzada.uq.citas_service.exceptions;

public final class UsuarioYaExisteException extends RuntimeException {

    public UsuarioYaExisteException(String mensaje) {
        super(mensaje);
    }

    public UsuarioYaExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public UsuarioYaExisteException(String username, boolean porUsername) {
        super("El nombre de usuario '" + username + "' ya está registrado en el sistema.");
    }

    public UsuarioYaExisteException(Long id) {
        super("El Usuario con ID " + id + " ya existe en el sistema.");
    }
}
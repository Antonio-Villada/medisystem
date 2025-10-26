package medisystem.avanzada.uq.citas_service.exceptions;

public final class UsuarioYaExisteException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public UsuarioYaExisteException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug)
    public UsuarioYaExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para indicar que el username ya existe (el más común).
    public UsuarioYaExisteException(String username, boolean porUsername) {
        // Usamos un flag booleano dummy para diferenciar del constructor simple (String mensaje).
        super("El nombre de usuario '" + username + "' ya está registrado en el sistema.");
    }

    // 4. Constructor especializado para indicar que el ID de usuario ya existe (menos común, pero útil en operaciones de PUT/POST).
    public UsuarioYaExisteException(Long id) {
        super("El Usuario con ID " + id + " ya existe en el sistema.");
    }
}
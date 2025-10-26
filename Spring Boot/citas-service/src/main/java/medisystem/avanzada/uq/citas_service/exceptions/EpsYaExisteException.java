package medisystem.avanzada.uq.citas_service.exceptions;

// Se recomienda que las excepciones de negocio sean 'final'.
public final class EpsYaExisteException extends RuntimeException {

    // 1. Constructor simple con mensaje (útil para errores genéricos)
    public EpsYaExisteException(String message) {
        super(message);
    }

    // 2. Constructor especializado para indicar que el nombre ya existe (el más usado en POST/PUT).
    // Usamos el flag booleano 'porNombre' para diferenciar la firma del constructor simple (String message).
    public EpsYaExisteException(String nombre, boolean porNombre) {
        super("La EPS '" + nombre + "' ya está registrada en el sistema.");
    }

    // 3. Constructor especializado para indicar que el ID ya existe (útil en operaciones de PUT/POST).
    // Usamos Long, ya que estandarizamos el ID de Eps a Long.
    public EpsYaExisteException(Long id) {
        super("La EPS con ID " + id + " ya existe en el sistema.");
    }
}
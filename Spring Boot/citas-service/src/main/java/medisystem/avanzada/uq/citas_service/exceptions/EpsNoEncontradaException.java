package medisystem.avanzada.uq.citas_service.exceptions;

public final class EpsNoEncontradaException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public EpsNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public EpsNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Clave Primaria: Long)
    // El ID de Eps fue estandarizado a Long.
    public EpsNoEncontradaException(Long id) {
        super("La EPS con ID " + id + " no fue encontrada en el sistema.");
    }

    // 4. Constructor especializado para buscar por Nombre (Clave de Negocio: String)
    public EpsNoEncontradaException(String nombre, boolean esNombre) {
        // Usamos un flag booleano dummy para diferenciar del constructor simple (String mensaje).
        super("La EPS '" + nombre + "' no fue encontrada en el sistema.");
    }
}
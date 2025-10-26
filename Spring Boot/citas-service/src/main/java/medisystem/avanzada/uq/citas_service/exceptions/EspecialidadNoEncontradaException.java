package medisystem.avanzada.uq.citas_service.exceptions;

public final class EspecialidadNoEncontradaException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public EspecialidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public EspecialidadNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Clave Primaria: Long)
    // El ID de Especialidad fue estandarizado a Long.
    public EspecialidadNoEncontradaException(Long id) {
        super("La Especialidad con ID " + id + " no fue encontrada en el sistema.");
    }

    // 4. Constructor especializado para buscar por Nombre (Clave de Negocio: String)
    public EspecialidadNoEncontradaException(String nombre, boolean esNombre) {
        // Usamos un flag booleano dummy para diferenciar del constructor simple (String mensaje).
        super("La Especialidad '" + nombre + "' no fue encontrada en el sistema.");
    }
}
package medisystem.avanzada.uq.citas_service.exceptions;

public final class FormulaNoEncontradaException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public FormulaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public FormulaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Clave Primaria: Long)
    // El ID de Formula fue estandarizado a Long.
    public FormulaNoEncontradaException(Long id) {
        super("La Fórmula con ID " + id + " no fue encontrada en el sistema.");
    }
}
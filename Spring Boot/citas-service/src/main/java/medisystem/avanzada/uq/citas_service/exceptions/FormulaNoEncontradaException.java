package medisystem.avanzada.uq.citas_service.exceptions;

public final class FormulaNoEncontradaException extends RuntimeException {

    public FormulaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public FormulaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public FormulaNoEncontradaException(Long id) {
        super("La Fórmula con ID " + id + " no fue encontrada en el sistema.");
    }
}
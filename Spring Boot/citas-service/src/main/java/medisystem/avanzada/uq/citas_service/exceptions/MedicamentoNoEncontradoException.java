package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicamentoNoEncontradoException extends RuntimeException {

    public MedicamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public MedicamentoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public MedicamentoNoEncontradoException(Long id) {
        super("El Medicamento con ID " + id + " no fue encontrado en el inventario.");
    }

    public MedicamentoNoEncontradoException(String nombre, boolean esNombre) {
        super("El Medicamento con nombre '" + nombre + "' no fue encontrado.");
    }
}
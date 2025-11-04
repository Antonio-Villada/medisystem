package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicamentoYaExisteException extends RuntimeException {

    public MedicamentoYaExisteException(String mensaje) {
        super(mensaje);
    }

    public MedicamentoYaExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public MedicamentoYaExisteException(String nombre, boolean porNombre) {
        super("El medicamento con nombre '" + nombre + "' ya está registrado en el inventario.");
    }

    public MedicamentoYaExisteException(Long id) {
        super("El Medicamento con ID " + id + " ya existe en el sistema.");
    }
}
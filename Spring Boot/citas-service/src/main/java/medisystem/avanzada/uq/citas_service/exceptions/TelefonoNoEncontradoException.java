package medisystem.avanzada.uq.citas_service.exceptions;

public final class TelefonoNoEncontradoException extends RuntimeException {


    public TelefonoNoEncontradoException(String mensaje, Throwable causa) {
        // Mantenemos este constructor ya que su firma es única (String, Throwable).
        super(mensaje, causa);
    }

    public TelefonoNoEncontradoException(Long id) {
        // Mantenemos este constructor ya que su firma es única (Long).
        super("El Teléfono con ID " + id + " no fue encontrado en el sistema.");
    }

    public TelefonoNoEncontradoException(String telefono) {
        super("El número de teléfono '" + telefono + "' no está registrado o no fue encontrado.");
    }
}
package medisystem.avanzada.uq.citas_service.exceptions;

// Recomendación: Es mejor que la excepción sea 'final' si solo extiende la funcionalidad base.
public final class TelefonoNoEncontradoException extends RuntimeException {

    // 1. ELIMINADO/COMENTADO: public TelefonoNoEncontradoException(String mensaje) {
    //    Este constructor es el que entra en conflicto con el Constructor 4 (String telefono).
    // }

    // 2. Constructor que envuelve otra excepción (útil para el debug).
    public TelefonoNoEncontradoException(String mensaje, Throwable causa) {
        // Mantenemos este constructor ya que su firma es única (String, Throwable).
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (el más común en repositorios).
    public TelefonoNoEncontradoException(Long id) {
        // Mantenemos este constructor ya que su firma es única (Long).
        super("El Teléfono con ID " + id + " no fue encontrado en el sistema.");
    }

    // 4. Constructor especializado para buscar por número de teléfono (String).
    // Este constructor ahora maneja todos los casos donde se pase un String simple.
    public TelefonoNoEncontradoException(String telefono) {
        super("El número de teléfono '" + telefono + "' no está registrado o no fue encontrado.");
    }
}
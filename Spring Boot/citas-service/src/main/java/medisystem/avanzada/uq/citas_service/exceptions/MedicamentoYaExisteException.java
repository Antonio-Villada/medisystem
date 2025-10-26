package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicamentoYaExisteException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public MedicamentoYaExisteException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug)
    public MedicamentoYaExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para indicar que el nombre del medicamento ya existe.
    // Usamos un flag booleano dummy para diferenciar del constructor simple (String mensaje).
    public MedicamentoYaExisteException(String nombre, boolean porNombre) {
        super("El medicamento con nombre '" + nombre + "' ya está registrado en el inventario.");
    }

    // 4. Constructor especializado para indicar que el ID de medicamento ya existe (menos común, pero útil).
    public MedicamentoYaExisteException(Long id) {
        super("El Medicamento con ID " + id + " ya existe en el sistema.");
    }
}
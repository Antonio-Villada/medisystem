package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicamentoNoEncontradoException extends RuntimeException {

    // 1. Constructor simple con mensaje (mantenido por flexibilidad)
    public MedicamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public MedicamentoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para buscar por ID (Clave Primaria: Long)
    public MedicamentoNoEncontradoException(Long id) {
        super("El Medicamento con ID " + id + " no fue encontrado en el inventario.");
    }

    // 4. Constructor especializado para buscar por Nombre (Clave de Negocio: String)
    // Usamos un flag booleano dummy para diferenciar del constructor simple (String mensaje).
    public MedicamentoNoEncontradoException(String nombre, boolean esNombre) {
        // Asumimos que el String pasado es el nombre del medicamento
        super("El Medicamento con nombre '" + nombre + "' no fue encontrado.");
    }
}
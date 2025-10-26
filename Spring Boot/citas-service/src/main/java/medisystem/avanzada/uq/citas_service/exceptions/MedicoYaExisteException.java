package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicoYaExisteException extends RuntimeException {

    // ELIMINAR O COMENTAR: public MedicoYaExisteException(String mensaje) { ... }
    // Ya que entra en conflicto con el constructor que recibe 'identificacion'.

    // 1. Constructor especializado para indicar que el ID/Identificación ya existe (String)
    // Asume que si se pasa un solo String, es el ID.
    public MedicoYaExisteException(String id) {
        super("El Médico con ID/Identificación " + id + " ya existe en el sistema.");
    }

    // 2. Constructor que envuelve otra excepción (para debug y traza de errores)
    public MedicoYaExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // 3. Constructor especializado para Correo Electrónico (Requiere un segundo parámetro para diferenciar la firma)
    // Ejemplo de cómo diferenciar: usando un flag booleano
    public MedicoYaExisteException(String correo, boolean esCorreo) {
        // Usamos un flag booleano dummy para diferenciar del constructor de solo String
        super("El Médico con correo " + correo + " ya existe en el sistema.");
    }

    // 4. Constructor especializado para indicar que el ID es un Long (si tu ID fuera numérico)
    // Puedes dejarlo si existe la posibilidad de buscar por ID numérico en tu Repositorio
    public MedicoYaExisteException(Long id, boolean porId) {
        // Usamos un flag booleano dummy para diferenciar del constructor de solo Long
        super("El Médico con ID " + id + " ya existe en el sistema.");
    }
}
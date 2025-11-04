package medisystem.avanzada.uq.citas_service.exceptions;

public final class MedicoNoEncontradoException extends RuntimeException {

    public MedicoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public MedicoNoEncontradoException(Long id) {
        super("El Médico con ID " + id + " no fue encontrado en el sistema.");
    }

    public MedicoNoEncontradoException(String username) {
        super("El Médico asociado al username '" + username + "' no fue encontrado.");
    }

    public MedicoNoEncontradoException(String correo, boolean esCorreo) {
        super("El Médico con correo '" + correo + "' no fue encontrado en el sistema.");
    }
}
package medisystem.avanzada.uq.citas_service.exceptions;

public final class PacienteNoEncontradoException extends RuntimeException {

    public PacienteNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public PacienteNoEncontradoException(String identificacion) {
        super("El Paciente con identificación " + identificacion + " no fue encontrado en el sistema.");
    }

    public PacienteNoEncontradoException(String correo, boolean esCorreo) {
        super("El Paciente con correo " + correo + " no fue encontrado en el sistema.");
    }
}
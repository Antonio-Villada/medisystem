package medisystem.avanzada.uq.citas_service.dto;

public class PacienteDTO {
    private String idPaciente;
    private String nombrePaciente;
    private String ciudad;
    private String correo;
    private String nombreEps;

    public PacienteDTO() {}

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreEps() { return nombreEps; }
    public void setNombreEps(String nombreEps) { this.nombreEps = nombreEps; }
}

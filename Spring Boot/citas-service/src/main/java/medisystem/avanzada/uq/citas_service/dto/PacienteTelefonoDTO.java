package medisystem.avanzada.uq.citas_service.dto;

public class PacienteTelefonoDTO {
    private Long id;
    private String idPaciente;
    private String telefono;

    public PacienteTelefonoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}

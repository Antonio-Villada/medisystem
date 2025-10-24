package medisystem.avanzada.uq.citas_service.dtos.medico;

public class MedicoResponseDTO {

    private Long idMedico;
    private String nombreMedico;
    private String telefono;
    private String correo;
    private String especialidad;
    private String username;
    private String rol;

    public MedicoResponseDTO() {
    }

    public MedicoResponseDTO(Long idMedico, String nombreMedico, String telefono, String correo,
                             String especialidad, String username, String rol) {
        this.idMedico = idMedico;
        this.nombreMedico = nombreMedico;
        this.telefono = telefono;
        this.correo = correo;
        this.especialidad = especialidad;
        this.username = username;
        this.rol = rol;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

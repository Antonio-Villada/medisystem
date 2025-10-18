package medisystem.avanzada.uq.citas_service.dto;

public class EspecialidadDTO {
    private Long idEspecialidad;
    private String nombreEspecialidad;

    public EspecialidadDTO() {}

    public Long getIdEspecialidad() { return idEspecialidad; }
    public void setIdEspecialidad(Long idEspecialidad) { this.idEspecialidad = idEspecialidad; }

    public String getNombreEspecialidad() { return nombreEspecialidad; }
    public void setNombreEspecialidad(String nombreEspecialidad) { this.nombreEspecialidad = nombreEspecialidad; }
}

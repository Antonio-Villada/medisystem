package medisystem.avanzada.uq.citas_service.dtos.formula;

public class FormulaRequestDTO {

    private String fecha;  // formato: "yyyy-MM-dd"
    private Integer idCita;

    public FormulaRequestDTO() {}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }
}

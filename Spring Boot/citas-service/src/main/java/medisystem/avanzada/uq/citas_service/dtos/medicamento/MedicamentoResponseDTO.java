package medisystem.avanzada.uq.citas_service.dtos.medicamento;

public class MedicamentoResponseDTO {

    private Integer idMedicamento;
    private String nombreMedicamento;
    private Integer precio;

    public MedicamentoResponseDTO() {}

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}

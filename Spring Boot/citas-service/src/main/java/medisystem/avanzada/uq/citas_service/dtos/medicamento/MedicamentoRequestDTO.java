package medisystem.avanzada.uq.citas_service.dtos.medicamento;

public class MedicamentoRequestDTO {

    private String nombreMedicamento;
    private Integer precio;

    public MedicamentoRequestDTO() {}

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

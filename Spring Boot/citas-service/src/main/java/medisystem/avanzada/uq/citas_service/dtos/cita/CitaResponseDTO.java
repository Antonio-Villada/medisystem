package medisystem.avanzada.uq.citas_service.dtos.cita;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;

public class CitaResponseDTO {

    private Integer idCita;
    private String fecha;       // formato: "yyyy-MM-dd"
    private String horaInicio;  // formato: "HH:mm"
    private String horaFin;     // formato: "HH:mm"
    private String observaciones;
    private MedicoResponseDTO medico;
    private PacienteResponseDTO paciente;

    public CitaResponseDTO() {}

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public MedicoResponseDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoResponseDTO medico) {
        this.medico = medico;
    }

    public PacienteResponseDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteResponseDTO paciente) {
        this.paciente = paciente;
    }
}

package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import java.util.List;

public interface PacienteService {
    List<Paciente> getPacientes();
    Paciente getPacienteById(String idPaciente);
    Paciente postPaciente(Paciente paciente);
    Paciente putPaciente(String idPaciente, Paciente paciente);
    void deletePaciente(String idPaciente);
    Paciente patchPaciente(String idPaciente, Paciente paciente);
}

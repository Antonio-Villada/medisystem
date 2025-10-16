package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.repositories.EpsRepository;
import medisystem.avanzada.uq.citas_service.repositories.PacienteRepository;

import medisystem.avanzada.uq.citas_service.service.impl.PacienteServiceImpl;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class PacienteServiceImplTest {

    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private EpsRepository epsRepository;

    void testGetPacientes() {
        List<Paciente> pacientes = pacienteServiceImpl.getPacientes();
        assertThat(pacientes.size()).isGreaterThanOrEqualTo(5);
    }
    @Test
    void testGetPacienteById() {
        Paciente existente = pacienteRepository.findAll().get(0);
        Paciente paciente = pacienteServiceImpl.getPacienteById(existente.getIdPaciente());
        assertThat(paciente.getIdPaciente()).isEqualTo(existente.getIdPaciente());
    }

    @Test
    void testPostPaciente() {
        Eps existente = epsRepository.findAll().get(0);

        Paciente nuevo = new Paciente();
        nuevo.setIdPaciente("123"); // 👈 ID manual
        nuevo.setNombrePaciente("Laura García");
        nuevo.setCiudad("Bogotá");
        nuevo.setCorreo("laura.garcia@test.com");
        nuevo.setEps(existente);

        Paciente guardado = pacienteServiceImpl.postPaciente(nuevo);

        assertThat(guardado.getIdPaciente()).isEqualTo("123");
        assertThat(guardado.getNombrePaciente()).isEqualTo("Laura García");
    }

    void testPutPaciente() {
        Eps epsExistente = epsRepository.findAll().get(0);

        Paciente pacienteExistente = pacienteRepository.findAll().get(0);
        Paciente actualizado = new Paciente();
        actualizado.setNombrePaciente("Nombre Actualizado");
        actualizado.setCiudad("Medellín");
        actualizado.setCorreo("nuevo.correo@test.com");
        actualizado.setEps(epsExistente);

        Paciente resultado = pacienteServiceImpl.putPaciente(pacienteExistente.getIdPaciente(), actualizado);

        assertThat(resultado.getNombrePaciente()).isEqualTo("Nombre Actualizado");
        assertThat(resultado.getCiudad()).isEqualTo("Medellín");
    }

    @Test
    void testPatchPaciente() {
        Paciente existente = pacienteRepository.findAll().get(0);
        Paciente parcial = new Paciente();
        parcial.setCiudad("Cali");

        Paciente resultado = pacienteServiceImpl.patchPaciente(existente.getIdPaciente(), parcial);

        assertThat(resultado.getCiudad()).isEqualTo("Cali");
    }

    @Test
    void testDeletePaciente() {
        Paciente existente = pacienteRepository.findAll().get(0);
        String id = existente.getIdPaciente();

        pacienteServiceImpl.deletePaciente(id);

        Optional<Paciente> eliminado = pacienteRepository.findById(id);
        assertThat(eliminado).isEmpty();
    }
    @Test
    void testGetPacienteByIdNotFound() {
        assertThrows(RuntimeException.class, () -> pacienteServiceImpl.getPacienteById("99999"));
    }

}

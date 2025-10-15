package medisystem.avanzada.uq.citas_service.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.repositories.EpsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EpsRepository epsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPacientes() throws Exception {
        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].idPaciente", notNullValue()));
    }

    @Test
    void testPostPaciente() throws Exception {
        Eps existente = epsRepository.findAll().get(0);

        Paciente nuevo = new Paciente();
        nuevo.setIdPaciente("PAC-999");
        nuevo.setNombrePaciente("Carlos López");
        nuevo.setCiudad("Cali");
        nuevo.setCorreo("carlos.lopez@test.com");
        nuevo.setEps(existente);

        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testPutPaciente() throws Exception {
        Eps existente = epsRepository.findAll().get(0);

        Paciente actualizado = new Paciente();
        actualizado.setNombrePaciente("María Actualizada");
        actualizado.setCiudad("Medellín");
        actualizado.setCorreo("maria.actualizada@test.com");
        actualizado.setEps(existente);

        mockMvc.perform(put("/pacientes/{idPaciente}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombrePaciente").value("María Actualizada"));
    }


    @Test
    void testDeletePaciente() throws Exception {
        mockMvc.perform(delete("/pacientes/{idPaciente}", "2"))
                .andExpect(status().isNoContent());
    }

}

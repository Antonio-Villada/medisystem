package medisystem.avanzada.uq.citas_service.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // usa application-test.properties con tu MySQL docker
public class MedicoControllerIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void GET_medicos_deberiaRetornarLista200OK() throws Exception {
        mockMvc.perform(get("/medicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].nombreMedico", notNullValue()));
    }


    @Test
    void GET_medicoPorId_existente_deberiaRetornar200OK() throws Exception {
        mockMvc.perform(get("/medicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreMedico").value("Dr. Juan Pérez"))
                .andExpect(jsonPath("$.especialidad.nombreEspecialidad").value("Cardiología Infantil"));
    }


    @Test
    void GET_medicoPorId_inexistente_deberiaRetornar404NotFound() throws Exception {
        mockMvc.perform(get("/medicos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrado")));
    }


    @Test
    void POST_medico_valido_deberiaCrearYRetornar201Created() throws Exception {
        Especialidad especialidad = new Especialidad(2L, "Dermatología Avanzada");
        Medico nuevo = new Medico(null, "Dr. Nuevo", especialidad, "3106666666", "nuevo@clinica.com");

        mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    @Test
    void PUT_medico_existente_deberiaActualizarYRetornar200OK() throws Exception {
        Especialidad esp = new Especialidad(3L, "Neurocirugía");
        Medico actualizado = new Medico(null, "Dr. Juan Actualizado", esp, "3100000000", "actualizado@clinica.com");

        mockMvc.perform(put("/medicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreMedico").value("Dr. Juan Actualizado"))
                .andExpect(jsonPath("$.especialidad.idEspecialidad").value(3));
    }


    @Test
    void PUT_medico_inexistente_deberiaRetornar404NotFound() throws Exception {
        Medico medico = new Medico(null, "Inexistente", null, "000", "no@no.com");

        mockMvc.perform(put("/medicos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrado")));
    }


    @Test
    void PATCH_medico_existente_deberiaActualizarParcialmente200OK() throws Exception {
        Medico parcial = new Medico();
        parcial.setTelefono("3000000000");

        mockMvc.perform(patch("/medicos/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telefono").value("3000000000"));
    }


    @Test
    void PATCH_medico_inexistente_deberiaRetornar404NotFound() throws Exception {
        Medico parcial = new Medico();
        parcial.setCorreo("no@no.com");

        mockMvc.perform(patch("/medicos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcial)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrado")));
    }


    @Test
    void DELETE_medico_existente_deberiaRetornar204NoContent() throws Exception {
        Especialidad esp = new Especialidad(1L, "Cardiología Infantil");
        Medico nuevo = new Medico(null, "Dr. Temporal", esp, "3111111111", "temp@clinica.com");

        // crear
        String location = mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        assert location != null;
        String id = location.substring(location.lastIndexOf("/") + 1);

        // eliminar
        mockMvc.perform(delete("/medicos/" + id))
                .andExpect(status().isNoContent());
    }


    @Test
    void DELETE_medico_inexistente_deberiaRetornar404NotFound() throws Exception {
        mockMvc.perform(delete("/medicos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrado")));
    }
}

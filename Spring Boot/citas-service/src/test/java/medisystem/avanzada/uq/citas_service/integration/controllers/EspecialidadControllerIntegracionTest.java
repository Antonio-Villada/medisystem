package medisystem.avanzada.uq.citas_service.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
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
@ActiveProfiles("test")
public class EspecialidadControllerIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void GET_especialidades_deberiaRetornarLista200OK() throws Exception {
        mockMvc.perform(get("/especialidad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].nombreEspecialidad", notNullValue()));
    }

    @Test
    void GET_especialidadPorId_inexistente_deberiaRetornar404NotFound() throws Exception {
        mockMvc.perform(get("/especialidad/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrada")));
    }


    @Test
    void POST_especialidad_valida_deberiaCrearYRetornar201Created() throws Exception {
        Especialidad nueva = new Especialidad(null, "Oncología");
        mockMvc.perform(post("/especialidad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    @Test
    void PUT_especialidad_existente_deberiaActualizarYRetornar200OK() throws Exception {
        Especialidad actualizada = new Especialidad(null, "Dermatología Avanzada");
        mockMvc.perform(put("/especialidad/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreEspecialidad").value("Dermatología Avanzada"));
    }


    @Test
    void PUT_especialidad_inexistente_deberiaRetornar404NotFound() throws Exception {
        Especialidad especialidad = new Especialidad(null, "Inexistente");
        mockMvc.perform(put("/especialidad/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(especialidad)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrada")));
    }


    @Test
    void DELETE_especialidad_existente_deberiaRetornar204NoContent() throws Exception {
        Especialidad nueva = new Especialidad(null, "Temporal");
        String json = objectMapper.writeValueAsString(nueva);

        // Crear primero
        String location = mockMvc.perform(post("/especialidad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // Extraer id
        assert location != null;
        String id = location.substring(location.lastIndexOf("/") + 1);

        // Eliminar
        mockMvc.perform(delete("/especialidad/" + id))
                .andExpect(status().isNoContent());
    }


    @Test
    void DELETE_especialidad_inexistente_deberiaRetornar404NotFound() throws Exception {
        mockMvc.perform(delete("/especialidad/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrada")));
    }


    @Test
    void PATCH_especialidad_existente_deberiaActualizarParcialmente200OK() throws Exception {
        Especialidad parcial = new Especialidad();
        parcial.setNombreEspecialidad("Cardiología Infantil");

        mockMvc.perform(patch("/especialidad/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreEspecialidad").value("Cardiología Infantil"));
    }


    @Test
    void PATCH_especialidad_inexistente_deberiaRetornar404NotFound() throws Exception {
        Especialidad parcial = new Especialidad();
        parcial.setNombreEspecialidad("Fisiatría");

        mockMvc.perform(patch("/especialidad/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcial)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("no encontrada")));
    }
}

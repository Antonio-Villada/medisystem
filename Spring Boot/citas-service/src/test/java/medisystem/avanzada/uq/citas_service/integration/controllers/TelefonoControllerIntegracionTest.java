//package medisystem.avanzada.uq.citas_service.integration.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import medisystem.avanzada.uq.citas_service.entities.Telefono;
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.hamcrest.Matchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@Import(TestSecurityConfig.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//public class TelefonoControllerIntegracionTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    void GET_telefonos_deberiaRetornarLista200OK() throws Exception {
//        mockMvc.perform(get("/telefonos"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", not(empty())))
//                .andExpect(jsonPath("$[0].telefono", notNullValue()));
//    }
//
//    @Test
//    void GET_telefonoPorId_inexistente_deberiaRetornar404NotFound() throws Exception {
//        mockMvc.perform(get("/telefonos/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//
//    @Test
//    void POST_telefono_valido_deberiaCrearYRetornar201Created() throws Exception {
//        Telefono nuevo = new Telefono(null, "3126666666");
//        mockMvc.perform(post("/telefonos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(nuevo)))
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"));
//    }
//
//    @Test
//    void PUT_telefono_existente_deberiaActualizarYRetornar200OK() throws Exception {
//        Telefono actualizado = new Telefono(null, "3127777777");
//        mockMvc.perform(put("/telefonos/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(actualizado)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.telefono").value("3127777777"));
//    }
//
//    @Test
//    void PUT_telefono_inexistente_deberiaRetornar404NotFound() throws Exception {
//        Telefono telefono = new Telefono(null, "3128888888");
//        mockMvc.perform(put("/telefonos/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(telefono)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//
//    @Test
//    void DELETE_telefono_existente_deberiaRetornar204NoContent() throws Exception {
//        Telefono nuevo = new Telefono(null, "3129999999");
//        String json = objectMapper.writeValueAsString(nuevo);
//
//        // Crear primero
//        String location = mockMvc.perform(post("/telefonos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andReturn()
//                .getResponse()
//                .getHeader("Location");
//
//        // Extraer id
//        assert location != null;
//        String id = location.substring(location.lastIndexOf("/") + 1);
//
//        // Eliminar
//        mockMvc.perform(delete("/telefonos/" + id))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void DELETE_telefono_inexistente_deberiaRetornar404NotFound() throws Exception {
//        mockMvc.perform(delete("/telefonos/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//
//    @Test
//    void PATCH_telefono_existente_deberiaActualizarParcialmente200OK() throws Exception {
//        Telefono parcial = new Telefono();
//        parcial.setTelefono("3121111119");
//
//        mockMvc.perform(patch("/telefonos/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parcial)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.telefono").value("3121111119"));
//    }
//
//    @Test
//    void PATCH_telefono_inexistente_deberiaRetornar404NotFound() throws Exception {
//        Telefono parcial = new Telefono();
//        parcial.setTelefono("3120000000");
//
//        mockMvc.perform(patch("/telefonos/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parcial)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//}

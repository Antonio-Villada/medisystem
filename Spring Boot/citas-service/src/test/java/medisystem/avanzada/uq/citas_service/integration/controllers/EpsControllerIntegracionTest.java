//package medisystem.avanzada.uq.citas_service.integration.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import medisystem.avanzada.uq.citas_service.entities.Eps;
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
//
//
//
//
//@Import(TestSecurityConfig.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//public class EpsControllerIntegracionTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    void GET_eps_deberiaRetornarLista200OK() throws Exception {
//        mockMvc.perform(get("/eps"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", not(empty())))
//                .andExpect(jsonPath("$[0].nombreEps", notNullValue()));
//    }
//
//    @Test
//    void GET_epsPorId_inexistente_deberiaRetornar404NotFound() throws Exception {
//        mockMvc.perform(get("/eps/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrada")));
//    }
//
//    @Test
//    void POST_eps_valida_deberiaCrearYRetornar201Created() throws Exception {
//        Eps nueva = new Eps(null, "Salud Total");
//        mockMvc.perform(post("/eps")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(nueva)))
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"));
//    }
//
//    @Test
//    void PUT_eps_existente_deberiaActualizarYRetornar200OK() throws Exception {
//        Eps actualizada = new Eps(null, "Nueva EPS Actualizada");
//        mockMvc.perform(put("/eps/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(actualizada)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nombreEps").value("Nueva EPS Actualizada"));
//    }
//
//    @Test
//    void PUT_eps_inexistente_deberiaRetornar404NotFound() throws Exception {
//        Eps eps = new Eps(null, "EPS Inexistente");
//        mockMvc.perform(put("/eps/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(eps)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrada")));
//    }
//
//    @Test
//    void DELETE_eps_existente_deberiaRetornar204NoContent() throws Exception {
//        Eps nueva = new Eps(null, "Temporal EPS");
//        String json = objectMapper.writeValueAsString(nueva);
//
//        // Crear primero
//        String location = mockMvc.perform(post("/eps")
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
//        mockMvc.perform(delete("/eps/" + id))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void DELETE_eps_inexistente_deberiaRetornar404NotFound() throws Exception {
//        mockMvc.perform(delete("/eps/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrada")));
//    }
//
//    @Test
//    void PATCH_eps_existente_deberiaActualizarParcialmente200OK() throws Exception {
//        Eps parcial = new Eps();
//        parcial.setNombreEps("Sura EPS");
//
//        mockMvc.perform(patch("/eps/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parcial)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nombreEps").value("Sura EPS"));
//    }
//
//    @Test
//    void PATCH_eps_inexistente_deberiaRetornar404NotFound() throws Exception {
//        Eps parcial = new Eps();
//        parcial.setNombreEps("Compensar");
//
//        mockMvc.perform(patch("/eps/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parcial)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrada")));
//    }
//}

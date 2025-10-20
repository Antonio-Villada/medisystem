//package medisystem.avanzada.uq.citas_service.integration.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import medisystem.avanzada.uq.citas_service.entities.Medicamento;
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
//@Import(TestSecurityConfig.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//public class MedicamentoControllerIntegracionTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    void GET_medicamentos_deberiaRetornarLista200OK() throws Exception {
//        mockMvc.perform(get("/medicamentos"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", not(empty())))
//                .andExpect(jsonPath("$[0].nombreMedicamento", notNullValue()));
//    }
//
//    @Test
//    void GET_medicamentoPorId_inexistente_deberiaRetornar404NotFound() throws Exception {
//        mockMvc.perform(get("/medicamentos/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//
//
//    @Test
//    void POST_medicamento_valido_deberiaCrearYRetornar201Created() throws Exception {
//        Medicamento nuevo = new Medicamento(null, "Naproxeno", 2200);
//        mockMvc.perform(post("/medicamentos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(nuevo)))
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"));
//    }
//
//
//    @Test
//    void PUT_medicamento_existente_deberiaActualizarYRetornar200OK() throws Exception {
//        Medicamento actualizado = new Medicamento(null, "Ibuprofeno Plus", 2100);
//        mockMvc.perform(put("/medicamentos/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(actualizado)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nombreMedicamento").value("Ibuprofeno Plus"))
//                .andExpect(jsonPath("$.precio").value(2100));
//    }
//
//
//    @Test
//    void PUT_medicamento_inexistente_deberiaRetornar404NotFound() throws Exception {
//        Medicamento medicamento = new Medicamento(null, "Inexistente", 1000);
//        mockMvc.perform(put("/medicamentos/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(medicamento)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//
//
//    @Test
//    void DELETE_medicamento_existente_deberiaRetornar204NoContent() throws Exception {
//        Medicamento nuevo = new Medicamento(null, "Temporal", 999);
//        String json = objectMapper.writeValueAsString(nuevo);
//
//        // Crear primero
//        String location = mockMvc.perform(post("/medicamentos")
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
//        mockMvc.perform(delete("/medicamentos/" + id))
//                .andExpect(status().isNoContent());
//    }
//
//
//    @Test
//    void DELETE_medicamento_inexistente_deberiaRetornar404NotFound() throws Exception {
//        mockMvc.perform(delete("/medicamentos/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//
//
//    @Test
//    void PATCH_medicamento_existente_deberiaActualizarParcialmente200OK() throws Exception {
//        Medicamento parcial = new Medicamento();
//        parcial.setNombreMedicamento("Amoxicilina Forte");
//        parcial.setPrecio(3200);
//
//        mockMvc.perform(patch("/medicamentos/3")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parcial)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nombreMedicamento").value("Amoxicilina Forte"))
//                .andExpect(jsonPath("$.precio").value(3200));
//    }
//
//
//    @Test
//    void PATCH_medicamento_inexistente_deberiaRetornar404NotFound() throws Exception {
//        Medicamento parcial = new Medicamento();
//        parcial.setNombreMedicamento("FármacoX");
//        parcial.setPrecio(5000);
//
//        mockMvc.perform(patch("/medicamentos/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parcial)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", containsString("no encontrado")));
//    }
//}

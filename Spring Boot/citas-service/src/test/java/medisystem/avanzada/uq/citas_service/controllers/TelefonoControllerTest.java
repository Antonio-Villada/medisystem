package medisystem.avanzada.uq.citas_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import medisystem.avanzada.uq.citas_service.entities.Telefono;
import medisystem.avanzada.uq.citas_service.exceptions.TelefonoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.services.TelefonoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TelefonoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TelefonoService telefonoService;

    @InjectMocks
    private TelefonoController telefonoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(telefonoController)
                .setControllerAdvice(new medisystem.avanzada.uq.citas_service.exceptions.GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetTelefonos() throws Exception {
        List<Telefono> lista = List.of(new Telefono(1, "3201234567"));
        when(telefonoService.getTelefonos()).thenReturn(lista);

        mockMvc.perform(get("/telefonos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].telefono").value("3201234567"));
    }

    @Test
    void testGetTelefonoById() throws Exception {
        Telefono telefono = new Telefono(1, "3109999999");
        when(telefonoService.getTelefonoById(1)).thenReturn(telefono);

        mockMvc.perform(get("/telefonos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telefono").value("3109999999"));
    }

    @Test
    void testGetTelefonoById_NoEncontrado() throws Exception {
        when(telefonoService.getTelefonoById(99))
                .thenThrow(new TelefonoNoEncontradoException("Teléfono con id 99 no encontrado"));

        mockMvc.perform(get("/telefonos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostTelefono() throws Exception {
        Telefono nuevo = new Telefono(1, "3001112233");
        when(telefonoService.postTelefono(any(Telefono.class))).thenReturn(nuevo);

        mockMvc.perform(post("/telefonos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/telefonos/1")));
    }

    @Test
    void testPutTelefono() throws Exception {
        Telefono actualizado = new Telefono(1, "3112223344");
        when(telefonoService.putTelefono(eq(1), any(Telefono.class))).thenReturn(actualizado);

        mockMvc.perform(put("/telefonos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telefono").value("3112223344"));
    }

    @Test
    void testDeleteTelefono() throws Exception {
        mockMvc.perform(delete("/telefonos/1"))
                .andExpect(status().isNoContent());

        verify(telefonoService).deleteTelefono(1);
    }

    @Test
    void testPatchTelefono() throws Exception {
        Telefono parcial = new Telefono(null, "3125556666");
        Telefono actualizado = new Telefono(1, "3125556666");
        when(telefonoService.patchTelefono(eq(1), any(Telefono.class))).thenReturn(actualizado);

        mockMvc.perform(patch("/telefonos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telefono").value("3125556666"));
    }
}

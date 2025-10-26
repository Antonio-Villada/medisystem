//package medisystem.avanzada.uq.citas_service.unit.controllers;
//
//import medisystem.avanzada.uq.citas_service.controllers.MedicoController;
//import medisystem.avanzada.uq.citas_service.entities.Especialidad;
//import medisystem.avanzada.uq.citas_service.entities.Medico;
//
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import medisystem.avanzada.uq.citas_service.service.MedicoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Transactional
//@Import(TestSecurityConfig.class)
//class MedicoControllerTest {
//
//    @Mock
//    private MedicoService medicoService;
//
//    @InjectMocks
//    private MedicoController medicoController;
//
//    private Medico medico;
//    private Especialidad especialidad;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        especialidad = new Especialidad(1L, "Cardiología");
//        medico = new Medico(1L, "Dr. Juan Pérez", especialidad, "123456789", "juan@correo.com");
//    }
//
//    @Test
//    void getMedicos() {
//        when(medicoService.getMedicos()).thenReturn(List.of(medico));
//
//        ResponseEntity<?> response = medicoController.getMedicos();
//
//        assertEquals(200, response.getStatusCodeValue());
//        List<Medico> lista = (List<Medico>) response.getBody();
//        assertEquals(1, lista.size());
//        verify(medicoService).getMedicos();
//    }
//
//    @Test
//    void getMedicoById() {
//        when(medicoService.getMedicoById(1L)).thenReturn(medico);
//
//        ResponseEntity<?> response = medicoController.getMedicoById(1L);
//
//        assertEquals(200, response.getStatusCodeValue());
//        Medico result = (Medico) response.getBody();
//        assertEquals("Dr. Juan Pérez", result.getNombreMedico());
//    }
//
//    @Test
//    void postMedico() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//        when(medicoService.postMedico(any(Medico.class))).thenReturn(medico);
//
//        ResponseEntity<?> response = medicoController.postMedico(medico);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertTrue(response.getHeaders().containsKey("Location"));
//        verify(medicoService).postMedico(any(Medico.class));
//    }
//
//    @Test
//    void putMedico() {
//        Medico actualizado = new Medico(1L, "Dr. Carlos Gómez", especialidad, "987654321", "carlos@correo.com");
//        when(medicoService.putMedico(eq(1L), any())).thenReturn(actualizado);
//
//        ResponseEntity<?> response = medicoController.putMedico(1L, actualizado);
//
//        assertEquals(200, response.getStatusCodeValue());
//        Medico result = (Medico) response.getBody();
//        assertEquals("Dr. Carlos Gómez", result.getNombreMedico());
//    }
//
//    @Test
//    void deleteMedico() {
//        doNothing().when(medicoService).deleteMedico(1L);
//
//        ResponseEntity<Void> response = medicoController.deleteMedico(1L);
//
//        assertEquals(204, response.getStatusCodeValue());
//        verify(medicoService).deleteMedico(1L);
//    }
//
//    @Test
//    void patchMedico() {
//        Medico parcial = new Medico(1L, "Dr. Actualizado", especialidad, null, null);
//        when(medicoService.patchMedico(eq(1L), any())).thenReturn(parcial);
//
//        ResponseEntity<Medico> response = medicoController.patchMedico(1L, parcial);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Dr. Actualizado", response.getBody().getNombreMedico());
//    }
//}

//package medisystem.avanzada.uq.citas_service.unit.controllers;
//
//import medisystem.avanzada.uq.citas_service.controllers.EspecialidadController;
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpStatus;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import medisystem.avanzada.uq.citas_service.entities.Especialidad;
//import medisystem.avanzada.uq.citas_service.service.EspecialidadService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Transactional
//@Import(TestSecurityConfig.class)
//class EspecialidadControllerTest {
//
//    @Mock
//    private EspecialidadService especialidadService;
//
//    @InjectMocks
//    private EspecialidadController especialidadController;
//
//    private Especialidad especialidad;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        especialidad = new Especialidad(1L, "Cardiología");
//    }
//
//    @Test
//    void getEspecialidad() {
//        when(especialidadService.getEspecialidades()).thenReturn(List.of(especialidad));
//
//        ResponseEntity<?> response = especialidadController.getEspecialidad();
//
//        assertEquals(200, response.getStatusCodeValue());
//        List<Especialidad> lista = (List<Especialidad>) response.getBody();
//        assertEquals(1, lista.size());
//        verify(especialidadService).getEspecialidades();
//    }
//
//    @Test
//    void getEspecialidadById() {
//        when(especialidadService.getEspecialidadByid(1L)).thenReturn(especialidad);
//
//        ResponseEntity<?> response = especialidadController.getEspecialidadById(1L);
//
//        assertEquals(200, response.getStatusCodeValue());
//        Especialidad result = (Especialidad) response.getBody();
//        assertEquals("Cardiología", result.getNombreEspecialidad());
//    }
////    @Test
////    void postEspecialidad() {
////        // Arrange
////        Especialidad especialidad = new Especialidad(1L, "Cardiología");
////        Especialidad especialidadGuardada = new Especialidad(1L, "Cardiología");
////
////        MockHttpServletRequest request = new MockHttpServletRequest();
////        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
////
////        when(especialidadService.postEspecialidad(any(Especialidad.class)))
////                .thenReturn(especialidadGuardada);
////
////        // Act
////        ResponseEntity<?> response = especialidadController.postEspecialidad(especialidad);
////
////        // Assert
////        assertEquals(HttpStatus.CREATED, response.getStatusCode());
////        assertNotNull(response.getBody());
////
////        // Si el controller retorna la entidad:
////        if (response.getBody() instanceof Especialidad body) {
////            assertEquals(especialidadGuardada.getIdEspecialidad(), body.getIdEspecialidad());
////            assertEquals(especialidadGuardada.getNombreEspecialidad(), body.getNombreEspecialidad());
////        } else {
////            // Si devuelve mensaje o URI
////            System.out.println("El controller no devuelve la entidad, sino: " + response.getBody());
////            assertTrue(response.getBody() instanceof String
////                    || response.getHeaders().containsKey("Location"));
////        }
////
////        verify(especialidadService, times(1)).postEspecialidad(any(Especialidad.class));
////    }
//
//
//
//    @Test
//    void putEspecialidad() {
//        Especialidad actualizada = new Especialidad(1L, "Dermatología");
//        when(especialidadService.putEspecialidad(eq(1L), any())).thenReturn(actualizada);
//
//        ResponseEntity<?> response = especialidadController.putEspecialidad(1L, actualizada);
//
//        assertEquals(200, response.getStatusCodeValue());
//        Especialidad result = (Especialidad) response.getBody();
//        assertEquals("Dermatología", result.getNombreEspecialidad());
//    }
//
//    @Test
//    void deleteEspecialidad() {
//        doNothing().when(especialidadService).deleteEspecialidad(1L);
//
//        ResponseEntity<Void> response = especialidadController.deleteEspecialidad(1L);
//
//        assertEquals(204, response.getStatusCodeValue());
//        verify(especialidadService).deleteEspecialidad(1L);
//    }
//
//    @Test
//    void patchEspecialidad() {
//        Especialidad parcial = new Especialidad(1L, "Neurología");
//        when(especialidadService.pachEspecialidad(eq(1L), any())).thenReturn(parcial);
//
//        ResponseEntity<Especialidad> response = especialidadController.patchEspecialidad(1L, parcial);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Neurología", response.getBody().getNombreEspecialidad());
//    }
//}

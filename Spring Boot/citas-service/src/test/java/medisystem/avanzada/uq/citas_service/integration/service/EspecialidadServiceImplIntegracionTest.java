//package medisystem.avanzada.uq.citas_service.integration.service;
//
//
//import medisystem.avanzada.uq.citas_service.entities.Especialidad;
//import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
//import medisystem.avanzada.uq.citas_service.service.impl.EspecialidadServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class EspecialidadServiceImplIntegracionTest {
//
//    @Autowired
//    private EspecialidadServiceImpl especialidadService;
//
//    @Test
//    void testFindEspecialidadById_EspecialidadExists() {
//        Long id = 2L;
//        Especialidad especialidad = especialidadService.getEspecialidadByid(id);
//        assertNotNull(especialidad);
//        assertEquals("Dermatología", especialidad.getNombreEspecialidad());
//    }
//
//    @Test
//    void getEspecialidades_deberiaRetornarListaDeEspecialidades() {
//        List<Especialidad> lista = especialidadService.getEspecialidades();
//        assertNotNull(lista);
//        assertFalse(lista.isEmpty());
//    }
//
//    @Test
//    void getEspecialidadById_existente_deberiaRetornarEspecialidad() {
//        Especialidad especialidad = especialidadService.getEspecialidadByid(2L);
//        assertNotNull(especialidad);
//        assertEquals("Dermatología", especialidad.getNombreEspecialidad());
//    }
//
//    @Test
//    void getEspecialidadById_inexistente_deberiaLanzarExcepcion() {
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.getEspecialidadByid(99L)
//        );
//    }
//
//    @Test
//    void postEspecialidad_deberiaGuardarYRetornarEspecialidad() {
//        Especialidad nueva = new Especialidad(null, "Oncología");
//        Especialidad guardada = especialidadService.postEspecialidad(nueva);
//
//        assertNotNull(guardada.getIdEspecialidad());
//        assertEquals("Oncología", guardada.getNombreEspecialidad());
//    }
//
//    @Test
//    void putEspecialidad_existente_deberiaActualizarNombre() {
//        Especialidad actualizada = new Especialidad(null, "Dermatología Avanzada");
//        Especialidad result = especialidadService.putEspecialidad(2L, actualizada);
//
//        assertEquals("Dermatología Avanzada", result.getNombreEspecialidad());
//    }
//
//    @Test
//    void putEspecialidad_inexistente_deberiaLanzarExcepcion() {
//        Especialidad especialidad = new Especialidad(null, "Inexistente");
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.putEspecialidad(99L, especialidad)
//        );
//    }
//
//    @Test
//    void deleteEspecialidad_existente_deberiaEliminarEspecialidad() {
//        Especialidad nueva = especialidadService.postEspecialidad(new Especialidad(null, "Temporal"));
//        Long id = nueva.getIdEspecialidad();
//
//        especialidadService.deleteEspecialidad(id);
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.getEspecialidadByid(id)
//        );
//    }
//
//    @Test
//    void deleteEspecialidad_inexistente_deberiaLanzarExcepcion() {
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.deleteEspecialidad(999L)
//        );
//    }
//
//    @Test
//    void patchEspecialidad_existente_deberiaActualizarParcialmente() {
//        Especialidad parcial = new Especialidad();
//        parcial.setNombreEspecialidad("Neurocirugía");
//
//        Especialidad actualizada = especialidadService.pachEspecialidad(3L, parcial);
//        assertEquals("Neurocirugía", actualizada.getNombreEspecialidad());
//    }
//
//    @Test
//    void patchEspecialidad_inexistente_deberiaLanzarExcepcion() {
//        Especialidad parcial = new Especialidad();
//        parcial.setNombreEspecialidad("Fisiatría");
//
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.pachEspecialidad(999L, parcial)
//        );
//    }
//
//}

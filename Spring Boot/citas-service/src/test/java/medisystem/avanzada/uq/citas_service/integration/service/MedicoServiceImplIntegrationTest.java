//package medisystem.avanzada.uq.citas_service.integration.service;
//
//import medisystem.avanzada.uq.citas_service.entities.Especialidad;
//import medisystem.avanzada.uq.citas_service.entities.Medico;
//import medisystem.avanzada.uq.citas_service.exceptions.MedicoNoEncontradoException;
//import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import medisystem.avanzada.uq.citas_service.service.impl.MedicoServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//
//@Import(TestSecurityConfig.class)
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class MedicoServiceImplIntegrationTest {
//
//    @Autowired
//    private MedicoServiceImpl medicoService;
//
//    @Autowired
//    private MedicoRepository medicoRepository;
//
//    @Test
//    void getMedicos_shouldReturnListOfMedicos() {
//        List<Medico> medicos = medicoService.getMedicos();
//        assertFalse(medicos.isEmpty());
//        assertEquals("Dr. Juan Pérez", medicos.get(0).getNombreMedico());
//    }
//
//    @Test
//    void getMedicoById_existingId_shouldReturnMedico() {
//        Medico medico = medicoService.getMedicoById(1L);
//        assertEquals("Dr. Juan Pérez", medico.getNombreMedico());
//    }
//
//    @Test
//    void getMedicoById_nonExistingId_shouldThrowException() {
//        assertThrows(MedicoNoEncontradoException.class, () -> medicoService.getMedicoById(999L));
//    }
//
//    @Test
//    void postMedico_shouldSaveNewMedico() {
//        Especialidad esp = new Especialidad(1L, "Cardiología Infantil");
//        Medico nuevo = new Medico(null, "Dr. Nuevo", esp, "3109999999", "nuevo@clinica.com");
//        Medico guardado = medicoService.postMedico(nuevo);
//
//        assertNotNull(guardado.getIdMedico());
//        assertEquals("Dr. Nuevo", guardado.getNombreMedico());
//    }
//
//    @Test
//    void putMedico_existing_shouldUpdateData() {
//        Medico original = medicoService.getMedicoById(2L);
//        original.setCorreo("update@correo.com");
//        Medico actualizado = medicoService.putMedico(2L, original);
//
//        assertEquals("update@correo.com", actualizado.getCorreo());
//    }
//
//    @Test
//    void deleteMedico_existing_shouldRemove() {
//        medicoService.deleteMedico(5L);
//        assertThrows(MedicoNoEncontradoException.class, () -> medicoService.getMedicoById(5L));
//    }
//
//    @Test
//    void patchMedico_shouldUpdatePartialData() {
//        Medico parcial = new Medico();
//        parcial.setTelefono("3108888888");
//        Medico actualizado = medicoService.patchMedico(1L, parcial);
//
//        assertEquals("3108888888", actualizado.getTelefono());
//    }
//}

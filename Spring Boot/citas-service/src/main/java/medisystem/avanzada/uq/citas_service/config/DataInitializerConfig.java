package medisystem.avanzada.uq.citas_service.config;

import medisystem.avanzada.uq.citas_service.entities.*;
import medisystem.avanzada.uq.citas_service.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component("dataInitializerConfig")
public class DataInitializerConfig {

    @Bean
    CommandLineRunner initDatabase(
            EspecialidadRepository especialidadRepository,
            EpsRepository epsRepository,
            MedicamentoRepository medicamentoRepository,
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            TelefonoRepository telefonoRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository,
            CitaRepository citaRepository,
            FormulaRepository formulaRepository,
            DetalleFormulaRepository detalleFormulaRepository
    ) {
        return args -> {

            // --------------------- ESPECIALIDADES ---------------------
            if (especialidadRepository.count() == 0) {
                especialidadRepository.saveAll(List.of(
                        new Especialidad(null, "Cardiología"),
                        new Especialidad(null, "Dermatología"),
                        new Especialidad(null, "Pediatría"),
                        new Especialidad(null, "Neurología"),
                        new Especialidad(null, "Oftalmología")
                ));
                System.out.println(" Especialidades insertadas");
            }

            // --------------------- EPS ---------------------
            if (epsRepository.count() == 0) {
                epsRepository.saveAll(List.of(
                        new Eps(null, "Sanitas"),
                        new Eps(null, "Sura"),
                        new Eps(null, "Coomeva"),
                        new Eps(null, "Nueva EPS"),
                        new Eps(null, "Compensar")
                ));
                System.out.println(" EPS insertadas");
            }

            // --------------------- MEDICAMENTOS ---------------------
            if (medicamentoRepository.count() == 0) {
                medicamentoRepository.saveAll(List.of(
                        new Medicamento(null, "Amoxicilina", 5000),
                        new Medicamento(null, "Ibuprofeno", 2000),
                        new Medicamento(null, "Paracetamol", 1500),
                        new Medicamento(null, "Loratadina", 3000),
                        new Medicamento(null, "Omeprazol", 4000)
                ));
                System.out.println(" Medicamentos insertados");
            }

            // --------------------- ROLES ---------------------
            if (rolRepository.count() == 0) {
                rolRepository.saveAll(List.of(
                        new Rol(RolNombre.ADMINISTRADOR),
                        new Rol(RolNombre.MEDICO),
                        new Rol(RolNombre.PACIENTE)
                ));
                System.out.println(" Roles insertados");
            }

            // --------------------- USUARIO ADMIN ---------------------
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Rol rolAdmin = rolRepository.findByNombre(RolNombre.ADMINISTRADOR)
                        .orElseThrow(() -> new RuntimeException("Rol ADMINISTRADOR no encontrado"));
                Usuario admin = new Usuario("admin", "{noop}12345");
                admin.getRoles().add(rolAdmin);
                usuarioRepository.save(admin);
                System.out.println(" Usuario administrador insertado");
            }

            // --------------------- PACIENTES ---------------------
            if (pacienteRepository.count() == 0) {
                Rol rolPaciente = rolRepository.findByNombre(RolNombre.PACIENTE)
                        .orElseThrow(() -> new RuntimeException("Rol PACIENTE no encontrado"));
                Eps eps1 = epsRepository.findAll().get(0);
                Eps eps2 = epsRepository.findAll().get(1);

                Usuario user1 = new Usuario("juanperez", "{noop}12345");
                user1.getRoles().add(rolPaciente);
                usuarioRepository.save(user1);

                Usuario user2 = new Usuario("anagomez", "{noop}12345");
                user2.getRoles().add(rolPaciente);
                usuarioRepository.save(user2);

                Usuario user3 = new Usuario("carlosruiz", "{noop}12345");
                user3.getRoles().add(rolPaciente);
                usuarioRepository.save(user3);

                pacienteRepository.saveAll(List.of(
                        new Paciente("P001", user1, "Juan Pérez", "Bogotá", "juan.perez@email.com", eps1),
                        new Paciente("P002", user2, "Ana Gómez", "Medellín", "ana.gomez@email.com", eps2),
                        new Paciente("P003", user3, "Carlos Ruiz", "Cali", "carlos.ruiz@email.com", eps1)
                ));
                System.out.println(" Pacientes insertados con sus usuarios");
            }

            // --------------------- MÉDICOS ---------------------
            if (medicoRepository.count() == 0) {
                Rol rolMedico = rolRepository.findByNombre(RolNombre.MEDICO)
                        .orElseThrow(() -> new RuntimeException("Rol MEDICO no encontrado"));
                Especialidad esp1 = especialidadRepository.findAll().get(0);
                Especialidad esp2 = especialidadRepository.findAll().get(1);

                Usuario medUser1 = new Usuario("juanlopez", "{noop}12345");
                medUser1.getRoles().add(rolMedico);
                usuarioRepository.save(medUser1);

                Usuario medUser2 = new Usuario("mariatorres", "{noop}12345");
                medUser2.getRoles().add(rolMedico);
                usuarioRepository.save(medUser2);

                medicoRepository.saveAll(List.of(
                        new Medico(null, "Dr. Juan López", medUser1, esp1, "3125551111", "juan.lopez@clinic.com"),
                        new Medico(null, "Dra. María Torres", medUser2, esp2, "3125552222", "maria.torres@clinic.com")
                ));
                System.out.println(" Médicos insertados con sus usuarios");
            }

            // --------------------- CITAS ---------------------
            if (citaRepository.count() == 0) {
                Medico medico1 = medicoRepository.findAll().get(0);
                Medico medico2 = medicoRepository.findAll().get(1);
                Paciente paciente1 = pacienteRepository.findById("P001")
                        .orElseThrow(() -> new RuntimeException("Paciente P001 no encontrado"));
                Paciente paciente2 = pacienteRepository.findById("P002")
                        .orElseThrow(() -> new RuntimeException("Paciente P002 no encontrado"));

                Cita cita1 = new Cita(null, LocalDate.now(), LocalTime.of(9, 0),
                        medico1, paciente1, "Chequeo general", null);
                Cita cita2 = new Cita(null, LocalDate.now().plusDays(1), LocalTime.of(10, 0),
                        medico2, paciente2, "Consulta dermatológica", null);

                citaRepository.saveAll(List.of(cita1, cita2));
                System.out.println(" Citas insertadas");

                // --------------------- FÓRMULAS ---------------------
                Formula formula1 = new Formula();
                formula1.setCita(cita1);
                formula1.setFecha(LocalDate.now());
                formulaRepository.save(formula1);

                Formula formula2 = new Formula();
                formula2.setCita(cita2);
                formula2.setFecha(LocalDate.now().plusDays(1));
                formulaRepository.save(formula2);
                System.out.println(" Fórmulas insertadas");

                // --------------------- DETALLES DE FÓRMULAS ---------------------
                Medicamento med1 = medicamentoRepository.findAll().get(0);
                Medicamento med2 = medicamentoRepository.findAll().get(1);

                detalleFormulaRepository.saveAll(List.of(
                        new DetalleFormula(null, formula1, med1, 1, "Cada 8 horas por 5 días"),
                        new DetalleFormula(null, formula2, med2, 2, "Cada 12 horas por 7 días")
                ));
                System.out.println(" Detalles de fórmulas insertados");
            }

            System.out.println(" ✅ Base de datos inicializada correctamente ");
        };
    }
}

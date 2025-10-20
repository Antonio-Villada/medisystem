package medisystem.avanzada.uq.citas_service.config;

import medisystem.avanzada.uq.citas_service.entities.*;
import medisystem.avanzada.uq.citas_service.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component("dataInitializerConfig")
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            EspecialidadRepository especialidadRepository,
            EpsRepository epsRepository,
            MedicamentoRepository medicamentoRepository,
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            TelefonoRepository telefonoRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository, // 🔹 Agregado aquí
            CitaRepository citaRepository,
            FormulaRepository formulaRepository,
            DetalleFormulaRepository detalleFormulaRepository
    ) {
        return args -> {

            // --------------------- ESPECIALIDADES ---------------------
            if (especialidadRepository.count() == 0) {
                especialidadRepository.save(new Especialidad(null, "Cardiología"));
                especialidadRepository.save(new Especialidad(null, "Dermatología"));
                especialidadRepository.save(new Especialidad(null, "Pediatría"));
                especialidadRepository.save(new Especialidad(null, "Neurología"));
                especialidadRepository.save(new Especialidad(null, "Oftalmología"));
                System.out.println(" Especialidades insertadas");
            }

            // --------------------- EPS ---------------------
            if (epsRepository.count() == 0) {
                epsRepository.save(new Eps(null, "Sanitas"));
                epsRepository.save(new Eps(null, "Sura"));
                epsRepository.save(new Eps(null, "Coomeva"));
                epsRepository.save(new Eps(null, "Nueva EPS"));
                epsRepository.save(new Eps(null, "Compensar"));
                System.out.println(" EPS insertadas");
            }

            // --------------------- MEDICAMENTOS ---------------------
            if (medicamentoRepository.count() == 0) {
                medicamentoRepository.save(new Medicamento(null, "Amoxicilina", 5000));
                medicamentoRepository.save(new Medicamento(null, "Ibuprofeno", 2000));
                medicamentoRepository.save(new Medicamento(null, "Paracetamol", 1500));
                medicamentoRepository.save(new Medicamento(null, "Loratadina", 3000));
                medicamentoRepository.save(new Medicamento(null, "Omeprazol", 4000));
                System.out.println(" Medicamentos insertados");
            }

            // --------------------- ROLES ---------------------
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol("ROLE_ADMIN"));
                rolRepository.save(new Rol("ROLE_USER"));
                System.out.println(" Roles insertados");
            }

            // --------------------- USUARIOS ---------------------
            if (usuarioRepository.count() == 0) {
                Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN").orElseThrow();
                Rol rolUser = rolRepository.findByNombre("ROLE_USER").orElseThrow();

                Usuario admin = new Usuario("admin", "{noop}12345");
                admin.getRoles().add(rolAdmin);
                usuarioRepository.save(admin);

                Usuario user = new Usuario("usuario", "{noop}12345");
                user.getRoles().add(rolUser);
                usuarioRepository.save(user);
                System.out.println(" Usuarios insertados");
            }

            // --------------------- TELEFONOS ---------------------
            if (telefonoRepository.count() == 0) {
                telefonoRepository.save(new Telefono(null, "3101111111"));
                telefonoRepository.save(new Telefono(null, "3202222222"));
                telefonoRepository.save(new Telefono(null, "3003333333"));
                telefonoRepository.save(new Telefono(null, "3014444444"));
                System.out.println(" Teléfonos insertados");
            }

            // --------------------- PACIENTES ---------------------
            if (pacienteRepository.count() == 0) {
                Eps eps1 = epsRepository.findAll().get(0);
                Eps eps2 = epsRepository.findAll().get(1);

                pacienteRepository.save(new Paciente("P001", "Juan Pérez", "Bogotá", "juan.perez@email.com", eps1));
                pacienteRepository.save(new Paciente("P002", "Ana Gómez", "Medellín", "ana.gomez@email.com", eps2));
                pacienteRepository.save(new Paciente("P003", "Carlos Ruiz", "Cali", "carlos.ruiz@email.com", eps1));
                System.out.println(" Pacientes insertados");
            }

            // --------------------- MÉDICOS ---------------------
            if (medicoRepository.count() == 0) {
                Especialidad esp1 = especialidadRepository.findAll().get(0);
                Especialidad esp2 = especialidadRepository.findAll().get(1);

                Medico medico1 = new Medico(null, "Dr. Juan López", esp1, "3125551111", "juan.lopez@clinic.com");
                Medico medico2 = new Medico(null, "Dra. María Torres", esp2, "3125552222", "maria.torres@clinic.com");

                medicoRepository.save(medico1);
                medicoRepository.save(medico2);
                System.out.println(" Médicos insertados");
            }

            // --------------------- CITAS ---------------------
            if (citaRepository.count() == 0) {
                Medico medico1 = medicoRepository.findAll().get(0);
                Medico medico2 = medicoRepository.findAll().get(1);

                Paciente paciente1 = pacienteRepository.findById("P001").orElseThrow();
                Paciente paciente2 = pacienteRepository.findById("P002").orElseThrow();

                Cita cita1 = new Cita(null,
                        LocalDate.now(),
                        LocalTime.of(9, 0),
                        medico1,
                        paciente1,
                        "Chequeo general",
                        null);

                Cita cita2 = new Cita(null,
                        LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0),
                        medico2,
                        paciente2,
                        "Consulta dermatológica",
                        null);

                citaRepository.save(cita1);
                citaRepository.save(cita2);
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

                detalleFormulaRepository.save(new DetalleFormula(null, formula1, med1, 1, "Cada 8 horas por 5 días"));
                detalleFormulaRepository.save(new DetalleFormula(null, formula2, med2, 2, "Cada 12 horas por 7 días"));
                System.out.println(" Detalles de fórmulas insertados");
            }

            System.out.println(" Base de datos inicializada correctamente ");
        };
    }
}

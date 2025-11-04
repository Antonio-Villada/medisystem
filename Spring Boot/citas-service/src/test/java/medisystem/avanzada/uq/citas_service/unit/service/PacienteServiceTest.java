package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.PacienteTelefono;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;
import medisystem.avanzada.uq.citas_service.entities.Telefono;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.exceptions.EpsNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.PacienteNoEncontradoException;
import medisystem.avanzada.uq.citas_service.exceptions.PacienteYaExisteException;
import medisystem.avanzada.uq.citas_service.mappers.PacienteMapper;
import medisystem.avanzada.uq.citas_service.repositories.EpsRepository;
import medisystem.avanzada.uq.citas_service.repositories.PacienteRepository;
import medisystem.avanzada.uq.citas_service.repositories.PacienteTelefonoRepository;
import medisystem.avanzada.uq.citas_service.repositories.TelefonoRepository;
import medisystem.avanzada.uq.citas_service.services.UserService;
import medisystem.avanzada.uq.citas_service.services.impl.PacienteServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    // Mocks de Repositorios y Servicios dependientes
    @Mock private PacienteRepository pacienteRepository;
    @Mock private EpsRepository epsRepository;
    @Mock private TelefonoRepository telefonoRepository;
    @Mock private PacienteTelefonoRepository pacienteTelefonoRepository;
    @Mock private UserService userService;
    @Mock private PacienteMapper pacienteMapper;

    // Clase a Probar
    @InjectMocks
    private PacienteServiceImpl pacienteService;

    // Variables de Prueba
    private final String PACIENTE_ID = "1094000101";
    private final Long EPS_ID_ANTIGUA = 5L;
    private final Long EPS_ID_NUEVA = 6L;
    private final String TELEFONO_NUEVO_NUMERO = "3001111111";
    private final String TELEFONO_EXISTENTE_NUMERO = "3102222222";
    private final String TELEFONO_ANTIGUO_NUMERO = "3203333333";
    private final String CORREO_ANTIGUO = "old@test.com";

    private Paciente pacienteExistente;
    private PacienteRequestDTO requestDTO;
    private PacienteResponseDTO responseDTO;
    private Eps epsEntityAntigua;
    private Eps epsEntityNueva;
    private Usuario usuarioEntity;
    private Telefono telefonoNuevoEntity;
    private Telefono telefonoExistenteEntity;

    @BeforeEach
    void setUp() {
        // --- 1. SET UP DE ENTIDADES DE SOPORTE ---
        epsEntityAntigua = new Eps(EPS_ID_ANTIGUA, "EPS Antigua");
        epsEntityNueva = new Eps(EPS_ID_NUEVA, "Nueva EPS");

        usuarioEntity = new Usuario();
        usuarioEntity.setUsername("paciente_test");

        // Teléfonos
        telefonoNuevoEntity = new Telefono(102L, TELEFONO_NUEVO_NUMERO);
        telefonoExistenteEntity = new Telefono(101L, TELEFONO_EXISTENTE_NUMERO);
        Telefono telefonoAntiguo = new Telefono(100L, TELEFONO_ANTIGUO_NUMERO); // Para simular el estado inicial

        // --- 2. PACIENTE EXISTENTE ---
        pacienteExistente = new Paciente();
        pacienteExistente.setIdPaciente(PACIENTE_ID);
        pacienteExistente.setNombrePaciente("Nombre Test");
        pacienteExistente.setCorreo(CORREO_ANTIGUO);
        pacienteExistente.setEps(epsEntityAntigua);
        pacienteExistente.setUsuario(usuarioEntity);
        // Lista de teléfonos antiguos para el estado inicial
        pacienteExistente.setTelefonos(List.of(new PacienteTelefono(1L, pacienteExistente, telefonoAntiguo)));

        // --- 3. DTO DE PETICIÓN (Datos de Actualización/Registro) ---
        requestDTO = new PacienteRequestDTO();
        requestDTO.setIdPaciente(PACIENTE_ID);
        requestDTO.setNombrePaciente("Nombre Actualizado");
        requestDTO.setCiudad("Nueva Ciudad");
        requestDTO.setCorreo("new@test.com");
        requestDTO.setIdEps(EPS_ID_NUEVA); // Cambia la EPS
        requestDTO.setUsername("paciente_test");
        requestDTO.setPassword("pass123");
        requestDTO.setTelefonos(Set.of(TELEFONO_NUEVO_NUMERO, TELEFONO_EXISTENTE_NUMERO)); // Nuevos teléfonos

        // --- 4. DTO DE RESPUESTA ESPERADO ---
        responseDTO = new PacienteResponseDTO(
                PACIENTE_ID, "Nombre Actualizado", "Nueva Ciudad", "new@test.com",
                "Nueva EPS", "paciente_test", requestDTO.getTelefonos()
        );
    }

    // ====================================================================
    // TEST DE LECTURA (GET)
    // ====================================================================

    @Test
    void getPacienteById_deberiaRetornarPaciente_cuandoExiste() {
        // Arrange
        // Configuración para el mapeo del Paciente con sus teléfonos antiguos
        Set<String> telefonosAntiguos = Set.of(TELEFONO_ANTIGUO_NUMERO);
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(pacienteExistente));
        when(pacienteMapper.toResponseDTO(eq(pacienteExistente), eq(telefonosAntiguos))).thenReturn(responseDTO);

        // Act
        PacienteResponseDTO resultado = pacienteService.getPacienteById(PACIENTE_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(PACIENTE_ID, resultado.getIdPaciente());
        verify(pacienteRepository, times(1)).findById(PACIENTE_ID);
    }

    @Test
    void getPacienteById_deberiaLanzarExcepcion_cuandoNoExiste() {
        // Arrange
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PacienteNoEncontradoException.class, () -> {
            pacienteService.getPacienteById(PACIENTE_ID);
        });
    }

    // ====================================================================
    // TEST DE ACTUALIZACIÓN TOTAL (PUT)
    // ====================================================================

    @Test
    void putPaciente_deberiaActualizarTodo_yManejarTelefonos() {
        // Arrange
        // 1. Buscar Paciente Existente: OK
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(pacienteExistente));
        // 2. Buscar Nueva EPS: OK
        when(epsRepository.findById(EPS_ID_NUEVA)).thenReturn(Optional.of(epsEntityNueva));

        // 3. Lógica de Teléfonos (Mocks para el flujo de actualización)
        when(telefonoRepository.findByTelefono(TELEFONO_NUEVO_NUMERO)).thenReturn(Optional.empty()); // Nuevo: debe crearse
        when(telefonoRepository.save(any(Telefono.class))).thenReturn(telefonoNuevoEntity);
        when(telefonoRepository.findByTelefono(TELEFONO_EXISTENTE_NUMERO)).thenReturn(Optional.of(telefonoExistenteEntity)); // Existente: debe reutilizarse

        // 4. Mapeo final
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteExistente);
        when(pacienteMapper.toResponseDTO(eq(pacienteExistente), eq(requestDTO.getTelefonos()))).thenReturn(responseDTO);

        // Act
        PacienteResponseDTO resultado = pacienteService.putPaciente(PACIENTE_ID, requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nombre Actualizado", resultado.getNombrePaciente());
        assertEquals("Nueva EPS", resultado.getNombreEps());

        // **Verificaciones Críticas de Teléfonos:**
        verify(pacienteTelefonoRepository, times(1)).deleteAllByPaciente(pacienteExistente); // Borrar antiguos
        verify(telefonoRepository, times(1)).save(any(Telefono.class)); // Guardar solo el nuevo
        verify(pacienteTelefonoRepository, times(2)).save(any(PacienteTelefono.class)); // Crear 2 nuevas relaciones
        verify(pacienteRepository, times(1)).save(pacienteExistente);
    }

    @Test
    void putPaciente_deberiaLanzarExcepcion_cuandoPacienteNoExiste() {
        // Arrange
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PacienteNoEncontradoException.class, () -> {
            pacienteService.putPaciente(PACIENTE_ID, requestDTO);
        });
        verify(pacienteRepository, never()).save(any());
    }


    // ====================================================================
    // TEST DE ELIMINACIÓN (DELETE)
    // ====================================================================

    @Test
    void deletePaciente_deberiaEliminarPaciente_cuandoExiste() {
        // Arrange
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(pacienteExistente));

        // Act
        pacienteService.deletePaciente(PACIENTE_ID);

        // Assert
        verify(pacienteRepository, times(1)).delete(pacienteExistente);
    }

    @Test
    void deletePaciente_deberiaLanzarExcepcion_cuandoNoExiste() {
        // Arrange
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PacienteNoEncontradoException.class, () -> {
            pacienteService.deletePaciente(PACIENTE_ID);
        });
        verify(pacienteRepository, never()).delete(any());
    }
}
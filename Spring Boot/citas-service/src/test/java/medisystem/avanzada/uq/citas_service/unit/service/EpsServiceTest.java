package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.dtos.eps.EpsRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.eps.EpsResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.exceptions.EpsNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.EpsYaExisteException;
import medisystem.avanzada.uq.citas_service.mappers.EpsMapper;
import medisystem.avanzada.uq.citas_service.repositories.EpsRepository;
import medisystem.avanzada.uq.citas_service.services.impl.EpsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Habilita la integración de Mockito con JUnit 5
@ExtendWith(MockitoExtension.class)
public class EpsServiceTest {

    // 1. Componentes Falsos (Mocks)
    @Mock
    private EpsRepository epsRepository;

    @Mock
    private EpsMapper epsMapper;

    // 2. Clase a Probar (Se inyectan automáticamente los mocks)
    @InjectMocks
    private EpsServiceImpl epsService;

    // 3. Variables de Prueba (Datos constantes)
    private final Long EPS_ID = 1L;
    private final String EPS_NOMBRE = "SALUD TOTAL";
    private Eps epsEntity;
    private EpsRequestDTO epsRequestDTO;
    private EpsResponseDTO epsResponseDTO;

    @BeforeEach
    void setUp() {
        // Inicializar entidades y DTOs comunes antes de cada prueba
        epsEntity = new Eps();
        epsEntity.setIdEps(EPS_ID);
        epsEntity.setNombreEps(EPS_NOMBRE);

        epsRequestDTO = new EpsRequestDTO();
        epsRequestDTO.setNombreEps(EPS_NOMBRE);

        epsResponseDTO = new EpsResponseDTO(EPS_ID, EPS_NOMBRE);
    }

    // --------------------------------------------------------------------
    // TEST 1: postEps - Creación Exitosa
    // --------------------------------------------------------------------
    @Test
    void postEps_deberiaCrearEps_cuandoNombreEsUnico() {
        // Arrange
        // Configurar el repositorio para decir que la EPS no existe
        when(epsRepository.existsByNombreEps(EPS_NOMBRE)).thenReturn(false);
        // Configurar el mapper para convertir Request a Entidad
        when(epsMapper.toEntity(epsRequestDTO)).thenReturn(epsEntity);
        // Configurar el repositorio para devolver la entidad guardada
        when(epsRepository.save(epsEntity)).thenReturn(epsEntity);
        // Configurar el mapper para convertir Entidad a Response
        when(epsMapper.toResponseDTO(epsEntity)).thenReturn(epsResponseDTO);

        // Act
        EpsResponseDTO resultado = epsService.postEps(epsRequestDTO);

        // Assert
        // Verificar que la respuesta es correcta
        assertNotNull(resultado);
        assertEquals(EPS_ID, resultado.getIdEps());
        // Verificar que la lógica de negocio se ejecutó (guardar y validar existencia)
        verify(epsRepository, times(1)).existsByNombreEps(EPS_NOMBRE);
        verify(epsRepository, times(1)).save(epsEntity);
    }

    // --------------------------------------------------------------------
    // TEST 2: postEps - Creación con Nombre Duplicado (Excepción)
    // --------------------------------------------------------------------
    @Test
    void postEps_deberiaLanzarExcepcion_cuandoEpsYaExiste() {
        // Arrange
        // Configurar el repositorio para decir que la EPS YA existe
        when(epsRepository.existsByNombreEps(EPS_NOMBRE)).thenReturn(true);

        // Act & Assert
        // Verificar que se lanza la excepción EpsYaExisteException
        assertThrows(EpsYaExisteException.class, () -> {
            epsService.postEps(epsRequestDTO);
        });

        // Verificar que el método save NUNCA fue llamado
        verify(epsRepository, never()).save(any(Eps.class));
    }

    // --------------------------------------------------------------------
    // TEST 3: getEpsById - Búsqueda Exitosa
    // --------------------------------------------------------------------
    @Test
    void getEpsById_deberiaRetornarEps_cuandoExiste() {
        // Arrange
        // Configurar el repositorio para encontrar la entidad
        when(epsRepository.findById(EPS_ID)).thenReturn(Optional.of(epsEntity));
        // Configurar el mapper para la conversión
        when(epsMapper.toResponseDTO(epsEntity)).thenReturn(epsResponseDTO);

        // Act
        EpsResponseDTO resultado = epsService.getEpsById(EPS_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(EPS_ID, resultado.getIdEps());
        // Verificar la interacción
        verify(epsRepository, times(1)).findById(EPS_ID);
    }

    // --------------------------------------------------------------------
    // TEST 4: getEpsById - Búsqueda No Encontrada (Excepción)
    // --------------------------------------------------------------------
    @Test
    void getEpsById_deberiaLanzarExcepcion_cuandoNoExiste() {
        // Arrange
        // Configurar el repositorio para devolver un Optional vacío
        when(epsRepository.findById(EPS_ID)).thenReturn(Optional.empty());

        // Act & Assert
        // Verificar que se lanza la excepción EpsNoEncontradaException
        assertThrows(EpsNoEncontradaException.class, () -> {
            epsService.getEpsById(EPS_ID);
        });
    }

    // --------------------------------------------------------------------
    // TEST 5: getAllEps - Retorno de Lista
    // --------------------------------------------------------------------
    @Test
    void getAllEps_deberiaRetornarListaDeEps() {
        // Arrange
        List<Eps> epsList = List.of(epsEntity);
        // Configurar el findAll
        when(epsRepository.findAll()).thenReturn(epsList);
        // Configurar el mapeo de cada elemento
        when(epsMapper.toResponseDTO(epsEntity)).thenReturn(epsResponseDTO);

        // Act
        List<EpsResponseDTO> resultado = epsService.getAllEps();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        // Verificar la interacción
        verify(epsRepository, times(1)).findAll();
    }
}
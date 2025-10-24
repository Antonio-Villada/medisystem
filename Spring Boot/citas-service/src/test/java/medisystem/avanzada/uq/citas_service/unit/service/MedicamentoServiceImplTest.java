package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.mappers.MedicamentoMapper;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
import medisystem.avanzada.uq.citas_service.service.impl.MedicamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@Import(TestSecurityConfig.class)
class MedicamentoServiceImplTest {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private MedicamentoMapper medicamentoMapper;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    private Medicamento medicamento;
    private MedicamentoRequestDTO requestDTO;
    private MedicamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicamento = new Medicamento(1, "Paracetamol", 1500);
        requestDTO = new MedicamentoRequestDTO();
        requestDTO.setNombreMedicamento("Paracetamol");
        requestDTO.setPrecio(1500);
        responseDTO = new MedicamentoResponseDTO();
        responseDTO.setIdMedicamento(1);
        responseDTO.setNombreMedicamento("Paracetamol");
        responseDTO.setPrecio(1500);
    }

    @Test
    void getMedicamentos() {
        when(medicamentoRepository.findAll()).thenReturn(List.of(medicamento));
        when(medicamentoMapper.toResponseDTO(any())).thenReturn(responseDTO);

        List<MedicamentoResponseDTO> result = medicamentoService.getMedicamentos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paracetamol", result.get(0).getNombreMedicamento());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    void getMedicamentoById() {
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicamentoMapper.toResponseDTO(medicamento)).thenReturn(responseDTO);

        MedicamentoResponseDTO result = medicamentoService.getMedicamentoById(1);

        assertNotNull(result);
        assertEquals("Paracetamol", result.getNombreMedicamento());
        verify(medicamentoRepository, times(1)).findById(1);
    }

    @Test
    void getMedicamentoById_inexistente() {
        when(medicamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(MedicamentoNoEncontradoException.class, () ->
                medicamentoService.getMedicamentoById(99));
    }

    @Test
    void postMedicamento() {
        when(medicamentoMapper.toEntity(requestDTO)).thenReturn(medicamento);
        when(medicamentoRepository.save(medicamento)).thenReturn(medicamento);
        when(medicamentoMapper.toResponseDTO(medicamento)).thenReturn(responseDTO);

        MedicamentoResponseDTO result = medicamentoService.postMedicamento(requestDTO);

        assertEquals("Paracetamol", result.getNombreMedicamento());
        verify(medicamentoRepository).save(medicamento);
    }

    @Test
    void putMedicamento() {
        MedicamentoRequestDTO nuevoDTO = new MedicamentoRequestDTO();
        nuevoDTO.setNombreMedicamento("Ibuprofeno");
        nuevoDTO.setPrecio(2000);

        Medicamento nuevo = new Medicamento(1, "Ibuprofeno", 2000);
        MedicamentoResponseDTO nuevoResponse = new MedicamentoResponseDTO();
        nuevoResponse.setIdMedicamento(1);
        nuevoResponse.setNombreMedicamento("Ibuprofeno");
        nuevoResponse.setPrecio(2000);

        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicamentoRepository.save(any())).thenReturn(nuevo);
        when(medicamentoMapper.toResponseDTO(any())).thenReturn(nuevoResponse);

        MedicamentoResponseDTO result = medicamentoService.putMedicamento(1, nuevoDTO);

        assertEquals("Ibuprofeno", result.getNombreMedicamento());
        verify(medicamentoRepository).save(any());
    }

    @Test
    void putMedicamento_inexistente() {
        when(medicamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(MedicamentoNoEncontradoException.class, () ->
                medicamentoService.putMedicamento(99, requestDTO));
    }

    @Test
    void deleteMedicamento() {
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));

        medicamentoService.deleteMedicamento(1);

        verify(medicamentoRepository).delete(medicamento);
    }

    @Test
    void deleteMedicamento_inexistente() {
        when(medicamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(MedicamentoNoEncontradoException.class, () ->
                medicamentoService.deleteMedicamento(99));
    }

    @Test
    void patchMedicamento() {
        MedicamentoRequestDTO patchDTO = new MedicamentoRequestDTO();
        patchDTO.setNombreMedicamento("Amoxicilina");
        patchDTO.setPrecio(3000);

        Medicamento actualizado = new Medicamento(1, "Amoxicilina", 3000);
        MedicamentoResponseDTO actualizadoDTO = new MedicamentoResponseDTO();
        actualizadoDTO.setIdMedicamento(1);
        actualizadoDTO.setNombreMedicamento("Amoxicilina");
        actualizadoDTO.setPrecio(3000);

        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicamentoRepository.save(any())).thenReturn(actualizado);
        when(medicamentoMapper.toResponseDTO(any())).thenReturn(actualizadoDTO);

        MedicamentoResponseDTO result = medicamentoService.patchMedicamento(1, patchDTO);

        assertEquals("Amoxicilina", result.getNombreMedicamento());
        assertEquals(3000, result.getPrecio());
    }

    @Test
    void patchMedicamento_inexistente() {
        when(medicamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(MedicamentoNoEncontradoException.class, () ->
                medicamentoService.patchMedicamento(99, requestDTO));
    }
}

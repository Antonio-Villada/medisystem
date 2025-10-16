package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.service.impl.MedicamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicamentoServiceImplTest {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    private Medicamento medicamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicamento = new Medicamento(1, "Paracetamol", 1500);
    }

    @Test
    void getMedicamentos() {
        when(medicamentoRepository.findAll()).thenReturn(List.of(medicamento));

        List<Medicamento> result = medicamentoService.getMedicamentos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paracetamol", result.get(0).getNombreMedicamento());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    void getMedicamentoById() {
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));

        Medicamento result = medicamentoService.getMedicamentoById(1);

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
        when(medicamentoRepository.save(medicamento)).thenReturn(medicamento);

        Medicamento result = medicamentoService.postMedicamento(medicamento);

        assertEquals("Paracetamol", result.getNombreMedicamento());
        verify(medicamentoRepository).save(medicamento);
    }

    @Test
    void putMedicamento() {
        Medicamento nuevo = new Medicamento(1, "Ibuprofeno", 2000);
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicamentoRepository.save(any())).thenReturn(nuevo);

        Medicamento result = medicamentoService.putMedicamento(1, nuevo);

        assertEquals("Ibuprofeno", result.getNombreMedicamento());
        verify(medicamentoRepository).save(any());
    }

    @Test
    void putMedicamento_inexistente() {
        when(medicamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(MedicamentoNoEncontradoException.class, () ->
                medicamentoService.putMedicamento(99, medicamento));
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
        Medicamento patch = new Medicamento();
        patch.setNombreMedicamento("Amoxicilina");
        patch.setPrecio(3000);

        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicamentoRepository.save(any())).thenReturn(patch);

        Medicamento result = medicamentoService.patchMedicamento(1, patch);

        assertEquals("Amoxicilina", result.getNombreMedicamento());
        assertEquals(3000, result.getPrecio());
    }

    @Test
    void patchMedicamento_inexistente() {
        when(medicamentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(MedicamentoNoEncontradoException.class, () ->
                medicamentoService.patchMedicamento(99, medicamento));
    }
}

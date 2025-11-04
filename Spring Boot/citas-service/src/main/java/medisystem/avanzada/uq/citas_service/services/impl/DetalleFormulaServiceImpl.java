package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.DetalleFormulaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.FormulaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.mappers.DetalleFormulaMapper;
import medisystem.avanzada.uq.citas_service.repositories.DetalleFormulaRepository;
import medisystem.avanzada.uq.citas_service.repositories.FormulaRepository;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.services.DetalleFormulaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("dbDetalleFormulaService")
@Transactional
public class DetalleFormulaServiceImpl implements DetalleFormulaService {

    private final DetalleFormulaRepository detalleFormulaRepository;
    private final FormulaRepository formulaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final DetalleFormulaMapper detalleFormulaMapper;

    public DetalleFormulaServiceImpl(DetalleFormulaRepository detalleFormulaRepository,
                                     FormulaRepository formulaRepository,
                                     MedicamentoRepository medicamentoRepository,
                                     DetalleFormulaMapper detalleFormulaMapper) {
        this.detalleFormulaRepository = detalleFormulaRepository;
        this.formulaRepository = formulaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.detalleFormulaMapper = detalleFormulaMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<DetalleFormulaResponseDTO> getDetalleFormulas() {
        return detalleFormulaRepository.findAll()
                .stream()
                .map(detalleFormulaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    // CORREGIDO: Integer -> Long
    public DetalleFormulaResponseDTO getDetalleFormulaById(Long idDetalleFormula) {
        DetalleFormula entity = detalleFormulaRepository.findById(idDetalleFormula)
                // Uso de la excepción con Long
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException(idDetalleFormula));

        return detalleFormulaMapper.toResponseDTO(entity);
    }



    @Override // Sobreescribe el nuevo método de la interfaz
    public DetalleFormulaResponseDTO postDetalleFormulaAnidado(Long idFormula, DetalleFormulaRequestDTO dto) {

        Formula formula = formulaRepository.findById(idFormula)
                .orElseThrow(() -> new FormulaNoEncontradaException(idFormula));


        Medicamento medicamento = medicamentoRepository.findById(dto.getIdMedicamento())
                .orElseThrow(() -> new MedicamentoNoEncontradoException(dto.getIdMedicamento()));


        DetalleFormula entity = detalleFormulaMapper.toEntity(dto, formula, medicamento);
        DetalleFormula saved = detalleFormulaRepository.save(entity);

        return detalleFormulaMapper.toResponseDTO(saved);
    }


    @Override
    public DetalleFormulaResponseDTO postDetalleFormula(DetalleFormulaRequestDTO dto) {
        // Asumiendo que esta llamada viene de un contexto donde el ID de la fórmula
        // se maneja externamente o se pasa implícitamente.
        throw new UnsupportedOperationException("El método POST directo de DetalleFormula está deshabilitado. Use el servicio de Fórmula.");
    }

    @Override
    public DetalleFormulaResponseDTO putDetalleFormula(Long idDetalleFormula, DetalleFormulaRequestDTO dto) {
        DetalleFormula existente = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException(idDetalleFormula));


        if (dto.getCantidad() != null) {
            existente.setCantidad(dto.getCantidad());
        }
        if (dto.getDosis() != null) {
            existente.setDosis(dto.getDosis());
        }

        DetalleFormula updated = detalleFormulaRepository.save(existente);
        return detalleFormulaMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteDetalleFormula(Long idDetalleFormula) {
        if (!detalleFormulaRepository.existsById(idDetalleFormula)) {
            throw new DetalleFormulaNoEncontradaException(idDetalleFormula);
        }
        detalleFormulaRepository.deleteById(idDetalleFormula);
    }

    @Override
    public DetalleFormulaResponseDTO patchDetalleFormula(Long idDetalleFormula, DetalleFormulaRequestDTO dto) {
        // Lógica de PATCH simplificada
        DetalleFormula existente = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException(idDetalleFormula));

        if (dto.getCantidad() != null) {
            existente.setCantidad(dto.getCantidad());
        }
        if (dto.getDosis() != null) {
            existente.setDosis(dto.getDosis());
        }

        DetalleFormula updated = detalleFormulaRepository.save(existente);
        return detalleFormulaMapper.toResponseDTO(updated);
    }
}
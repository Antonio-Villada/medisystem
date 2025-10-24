package medisystem.avanzada.uq.citas_service.service.impl;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.DetalleFormulaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.mappers.DetalleFormulaMapper;
import medisystem.avanzada.uq.citas_service.mappers.FormulaMapper;
import medisystem.avanzada.uq.citas_service.mappers.MedicamentoMapper;
import medisystem.avanzada.uq.citas_service.repositories.DetalleFormulaRepository;
import medisystem.avanzada.uq.citas_service.repositories.FormulaRepository;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.service.DetalleFormulaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("dbDetalleFormulaService")
public class DetalleFormulaServiceImpl implements DetalleFormulaService {

    private final DetalleFormulaRepository detalleFormulaRepository;
    private final FormulaRepository formulaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final DetalleFormulaMapper detalleFormulaMapper;
    private final FormulaMapper formulaMapper;
    private final MedicamentoMapper medicamentoMapper;

    public DetalleFormulaServiceImpl(DetalleFormulaRepository detalleFormulaRepository,
                                     FormulaRepository formulaRepository,
                                     MedicamentoRepository medicamentoRepository,
                                     DetalleFormulaMapper detalleFormulaMapper,
                                     FormulaMapper formulaMapper,
                                     MedicamentoMapper medicamentoMapper) {
        this.detalleFormulaRepository = detalleFormulaRepository;
        this.formulaRepository = formulaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.detalleFormulaMapper = detalleFormulaMapper;
        this.formulaMapper = formulaMapper;
        this.medicamentoMapper = medicamentoMapper;
    }

    @Override
    public List<DetalleFormulaResponseDTO> getDetalleFormulas() {
        return detalleFormulaRepository.findAll()
                .stream()
                .map(df -> {
                    FormulaResponseDTO formulaDTO = formulaMapper.toResponseDTO(df.getFormula(), null);
                    MedicamentoResponseDTO medDTO = medicamentoMapper.toResponseDTO(df.getMedicamento());
                    return detalleFormulaMapper.toResponseDTO(df, formulaDTO, medDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    public DetalleFormulaResponseDTO getDetalleFormulaById(Integer idDetalleFormula) {
        DetalleFormula entity = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException(
                        "DetalleFormula con id " + idDetalleFormula + " no encontrada"));

        FormulaResponseDTO formulaDTO = formulaMapper.toResponseDTO(entity.getFormula(), null);
        MedicamentoResponseDTO medDTO = medicamentoMapper.toResponseDTO(entity.getMedicamento());
        return detalleFormulaMapper.toResponseDTO(entity, formulaDTO, medDTO);
    }

    @Override
    public DetalleFormulaResponseDTO postDetalleFormula(DetalleFormulaRequestDTO dto) {
        Formula formula = formulaRepository.findById(dto.getIdFormula())
                .orElseThrow(() -> new IllegalArgumentException("Fórmula con id " + dto.getIdFormula() + " no encontrada"));
        Medicamento medicamento = medicamentoRepository.findById(dto.getIdMedicamento())
                .orElseThrow(() -> new IllegalArgumentException("Medicamento con id " + dto.getIdMedicamento() + " no encontrado"));

        DetalleFormula entity = detalleFormulaMapper.toEntity(dto, formula, medicamento);
        DetalleFormula saved = detalleFormulaRepository.save(entity);

        FormulaResponseDTO formulaDTO = formulaMapper.toResponseDTO(formula, null);
        MedicamentoResponseDTO medDTO = medicamentoMapper.toResponseDTO(medicamento);
        return detalleFormulaMapper.toResponseDTO(saved, formulaDTO, medDTO);
    }

    @Override
    public DetalleFormulaResponseDTO putDetalleFormula(Integer idDetalleFormula, DetalleFormulaRequestDTO dto) {
        DetalleFormula existente = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException(
                        "DetalleFormula con id " + idDetalleFormula + " no encontrada"));

        Formula formula = formulaRepository.findById(dto.getIdFormula())
                .orElseThrow(() -> new IllegalArgumentException("Fórmula con id " + dto.getIdFormula() + " no encontrada"));
        Medicamento medicamento = medicamentoRepository.findById(dto.getIdMedicamento())
                .orElseThrow(() -> new IllegalArgumentException("Medicamento con id " + dto.getIdMedicamento() + " no encontrado"));

        existente.setFormula(formula);
        existente.setMedicamento(medicamento);
        existente.setCantidad(dto.getCantidad());
        existente.setDosis(dto.getDosis());

        DetalleFormula updated = detalleFormulaRepository.save(existente);

        FormulaResponseDTO formulaDTO = formulaMapper.toResponseDTO(formula, null);
        MedicamentoResponseDTO medDTO = medicamentoMapper.toResponseDTO(medicamento);
        return detalleFormulaMapper.toResponseDTO(updated, formulaDTO, medDTO);
    }

    @Override
    public void deleteDetalleFormula(Integer idDetalleFormula) {
        DetalleFormula existente = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException(
                        "DetalleFormula con id " + idDetalleFormula + " no encontrada"));
        detalleFormulaRepository.delete(existente);
    }

    @Override
    public DetalleFormulaResponseDTO patchDetalleFormula(Integer idDetalleFormula, DetalleFormulaRequestDTO dto) {
        DetalleFormula existente = detalleFormulaRepository.findById(idDetalleFormula)
                .orElseThrow(() -> new DetalleFormulaNoEncontradaException(
                        "DetalleFormula con id " + idDetalleFormula + " no encontrada"));

        if (dto.getIdFormula() != null) {
            Formula formula = formulaRepository.findById(dto.getIdFormula())
                    .orElseThrow(() -> new IllegalArgumentException("Fórmula con id " + dto.getIdFormula() + " no encontrada"));
            existente.setFormula(formula);
        }
        if (dto.getIdMedicamento() != null) {
            Medicamento medicamento = medicamentoRepository.findById(dto.getIdMedicamento())
                    .orElseThrow(() -> new IllegalArgumentException("Medicamento con id " + dto.getIdMedicamento() + " no encontrado"));
            existente.setMedicamento(medicamento);
        }
        if (dto.getCantidad() != null) {
            existente.setCantidad(dto.getCantidad());
        }
        if (dto.getDosis() != null) {
            existente.setDosis(dto.getDosis());
        }

        DetalleFormula actualizado = detalleFormulaRepository.save(existente);

        FormulaResponseDTO formulaDTO = formulaMapper.toResponseDTO(actualizado.getFormula(), null);
        MedicamentoResponseDTO medDTO = medicamentoMapper.toResponseDTO(actualizado.getMedicamento());
        return detalleFormulaMapper.toResponseDTO(actualizado, formulaDTO, medDTO);
    }
}

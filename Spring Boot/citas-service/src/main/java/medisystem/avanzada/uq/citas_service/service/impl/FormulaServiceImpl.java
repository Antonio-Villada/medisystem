package medisystem.avanzada.uq.citas_service.service.impl;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.exceptions.CitaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.FormulaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.mappers.CitaMapper;
import medisystem.avanzada.uq.citas_service.mappers.FormulaMapper;
import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
import medisystem.avanzada.uq.citas_service.repositories.FormulaRepository;
import medisystem.avanzada.uq.citas_service.service.FormulaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormulaServiceImpl implements FormulaService {

    private final FormulaRepository formulaRepository;
    private final CitaRepository citaRepository;
    private final FormulaMapper formulaMapper;
    private final CitaMapper citaMapper;

    public FormulaServiceImpl(FormulaRepository formulaRepository,
                              CitaRepository citaRepository,
                              FormulaMapper formulaMapper,
                              CitaMapper citaMapper) {
        this.formulaRepository = formulaRepository;
        this.citaRepository = citaRepository;
        this.formulaMapper = formulaMapper;
        this.citaMapper = citaMapper;
    }

    // ==========================================================
    // Métodos nuevos con DTOs
    // ==========================================================
    @Override
    public List<FormulaResponseDTO> findAll() {
        return formulaRepository.findAll()
                .stream()
                .map(formula -> {
                    Cita cita = formula.getCita();
                    CitaResponseDTO citaDTO = citaMapper.toResponseDTO(cita, null, null);
                    return formulaMapper.toResponseDTO(formula, citaDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    public FormulaResponseDTO findById(int id) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new FormulaNoEncontradaException("Fórmula con id " + id + " no encontrada"));
        CitaResponseDTO citaDTO = citaMapper.toResponseDTO(formula.getCita(), null, null);
        return formulaMapper.toResponseDTO(formula, citaDTO);
    }

    @Override
    public FormulaResponseDTO save(FormulaRequestDTO dto) {
        Cita cita = citaRepository.findById(dto.getIdCita())
                .orElseThrow(() -> new CitaNoEncontradaException("Cita no encontrada para id " + dto.getIdCita()));

        Formula formula = formulaMapper.toEntity(dto, cita);
        Formula guardada = formulaRepository.save(formula);

        CitaResponseDTO citaDTO = citaMapper.toResponseDTO(guardada.getCita(), null, null);
        return formulaMapper.toResponseDTO(guardada, citaDTO);
    }

    // ==========================================================
    // Métodos originales con la entidad (compatibilidad)
    // ==========================================================
    @Override
    public Formula update(int id, Formula formula) {
        Formula existente = formulaRepository.findById(id)
                .orElseThrow(() -> new FormulaNoEncontradaException("Fórmula con id " + id + " no encontrada"));
        existente.setFecha(formula.getFecha());
        existente.setCita(formula.getCita());
        return formulaRepository.save(existente);
    }

    @Override
    public void delete(int id) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new FormulaNoEncontradaException("Fórmula con id " + id + " no encontrada"));
        formulaRepository.delete(formula);
    }
}

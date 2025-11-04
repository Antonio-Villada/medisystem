package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.exceptions.CitaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.FormulaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.mappers.FormulaMapper;
import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
import medisystem.avanzada.uq.citas_service.repositories.FormulaRepository;
import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
import medisystem.avanzada.uq.citas_service.services.FormulaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FormulaServiceImpl implements FormulaService {

    private final FormulaRepository formulaRepository;
    private final CitaRepository citaRepository;
    private final FormulaMapper formulaMapper;
    private final MedicoRepository medicoRepository;

    public FormulaServiceImpl(FormulaRepository formulaRepository,
                              CitaRepository citaRepository,
                              FormulaMapper formulaMapper,
                              MedicoRepository medicoRepository) {
        this.formulaRepository = formulaRepository;
        this.citaRepository = citaRepository;
        this.formulaMapper = formulaMapper;
        this.medicoRepository = medicoRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<FormulaResponseDTO> findAll() {
        return formulaRepository.findAll()
                .stream()
                .map(formulaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FormulaResponseDTO findById(Long id) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new FormulaNoEncontradaException(id));

        return formulaMapper.toResponseDTO(formula);
    }


    @Override
    public FormulaResponseDTO save(FormulaRequestDTO dto) {
        Cita cita = citaRepository.findById(dto.getIdCita())
                .orElseThrow(() -> new CitaNoEncontradaException(dto.getIdCita()));

        if (cita.getFormula() != null) {
            throw new RuntimeException("La cita con ID " + dto.getIdCita() + " ya tiene una fórmula asociada.");
        }

        Formula formula = formulaMapper.toEntity(dto, cita);
        Formula guardada = formulaRepository.save(formula);

        return formulaMapper.toResponseDTO(guardada);
    }


    @Override
    public FormulaResponseDTO update(Long id, FormulaRequestDTO dto) {
        Formula existente = formulaRepository.findById(id)
                .orElseThrow(() -> new FormulaNoEncontradaException(id));

        verificarPropiedadMedico(existente);

        if (dto.getFecha() != null) {
            existente.setFecha(dto.getFecha());
        }

        return formulaMapper.toResponseDTO(formulaRepository.save(existente));
    }


    @Override
    public void delete(Long id) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new FormulaNoEncontradaException(id));

        verificarPropiedadMedico(formula);
        formulaRepository.delete(formula);
    }

    private void verificarPropiedadMedico(Formula formula) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Medico medicoActual = medicoRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new MedicoNoEncontradoException(username));

        Medico medicoDeFormula = formula.getCita().getMedico();
        if (!medicoDeFormula.getIdMedico().equals(medicoActual.getIdMedico())) {
            throw new RuntimeException("No tienes permiso para modificar o eliminar la fórmula de otro médico.");
        }
    }
}
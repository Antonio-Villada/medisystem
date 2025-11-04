package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoYaExisteException; // Importación necesaria
import medisystem.avanzada.uq.citas_service.mappers.MedicamentoMapper;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.services.MedicamentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("dbMedicamentoService")
@Transactional
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final MedicamentoMapper medicamentoMapper;

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository,
                                  MedicamentoMapper medicamentoMapper) {
        this.medicamentoRepository = medicamentoRepository;
        this.medicamentoMapper = medicamentoMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<MedicamentoResponseDTO> getMedicamentos() {
        return medicamentoRepository.findAll()
                .stream()
                .map(medicamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MedicamentoResponseDTO getMedicamentoById(Long idMedicamento) {
        Medicamento medicamento = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(idMedicamento));
        return medicamentoMapper.toResponseDTO(medicamento);
    }

    @Override
    public MedicamentoResponseDTO postMedicamento(MedicamentoRequestDTO medicamentoDTO) {
        if (medicamentoRepository.existsByNombreMedicamento(medicamentoDTO.getNombreMedicamento())) {
            throw new MedicamentoYaExisteException(medicamentoDTO.getNombreMedicamento(), true);
        }

        Medicamento entity = medicamentoMapper.toEntity(medicamentoDTO);
        Medicamento saved = medicamentoRepository.save(entity);
        return medicamentoMapper.toResponseDTO(saved);
    }


    @Override
    public MedicamentoResponseDTO putMedicamento(Long idMedicamento, MedicamentoRequestDTO medicamentoDTO) {
        Medicamento existente = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(idMedicamento));

        if (!existente.getNombreMedicamento().equalsIgnoreCase(medicamentoDTO.getNombreMedicamento())) {
            // CORRECCIÓN 2: existsByNombre -> existsByNombreMedicamento
            if (medicamentoRepository.existsByNombreMedicamento(medicamentoDTO.getNombreMedicamento())) {
                throw new MedicamentoYaExisteException(medicamentoDTO.getNombreMedicamento(), true);
            }
        }

        existente.setNombreMedicamento(medicamentoDTO.getNombreMedicamento());
        existente.setPrecio(medicamentoDTO.getPrecio());

        Medicamento actualizado = medicamentoRepository.save(existente);
        return medicamentoMapper.toResponseDTO(actualizado);
    }


    @Override
    public void deleteMedicamento(Long idMedicamento) {
        // Verificamos existencia para lanzar 404
        if (!medicamentoRepository.existsById(idMedicamento)) {
            throw new MedicamentoNoEncontradoException(idMedicamento);
        }
        medicamentoRepository.deleteById(idMedicamento);
    }


    @Override
    public MedicamentoResponseDTO patchMedicamento(Long idMedicamento, MedicamentoRequestDTO medicamentoDTO) {
        Medicamento existente = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(idMedicamento));

        if (medicamentoDTO.getNombreMedicamento() != null) {
            // Validación de Negocio: Si se proporciona un nombre, debe ser único
            if (!existente.getNombreMedicamento().equalsIgnoreCase(medicamentoDTO.getNombreMedicamento())) {
                // CORRECCIÓN 3: existsByNombre -> existsByNombreMedicamento
                if (medicamentoRepository.existsByNombreMedicamento(medicamentoDTO.getNombreMedicamento())) {
                    throw new MedicamentoYaExisteException(medicamentoDTO.getNombreMedicamento(), true);
                }
            }
            existente.setNombreMedicamento(medicamentoDTO.getNombreMedicamento());
        }
        if (medicamentoDTO.getPrecio() != null) {
            existente.setPrecio(medicamentoDTO.getPrecio());
        }

        Medicamento actualizado = medicamentoRepository.save(existente);
        return medicamentoMapper.toResponseDTO(actualizado);
    }
}
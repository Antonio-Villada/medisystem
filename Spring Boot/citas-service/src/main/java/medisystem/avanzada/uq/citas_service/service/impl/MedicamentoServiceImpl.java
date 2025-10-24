package medisystem.avanzada.uq.citas_service.service.impl;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.mappers.MedicamentoMapper;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.service.MedicamentoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("dbMedicamentoService")
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final MedicamentoMapper medicamentoMapper;

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository,
                                  MedicamentoMapper medicamentoMapper) {
        this.medicamentoRepository = medicamentoRepository;
        this.medicamentoMapper = medicamentoMapper;
    }

    @Override
    public List<MedicamentoResponseDTO> getMedicamentos() {
        return medicamentoRepository.findAll()
                .stream()
                .map(medicamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoResponseDTO getMedicamentoById(Integer idMedicamento) {
        Medicamento medicamento = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(
                        "Medicamento con id " + idMedicamento + " no encontrado"));
        return medicamentoMapper.toResponseDTO(medicamento);
    }

    @Override
    public MedicamentoResponseDTO postMedicamento(MedicamentoRequestDTO medicamentoDTO) {
        Medicamento entity = medicamentoMapper.toEntity(medicamentoDTO);
        Medicamento saved = medicamentoRepository.save(entity);
        return medicamentoMapper.toResponseDTO(saved);
    }

    @Override
    public MedicamentoResponseDTO putMedicamento(Integer idMedicamento, MedicamentoRequestDTO medicamentoDTO) {
        Medicamento existente = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(
                        "Medicamento con id " + idMedicamento + " no encontrado"));

        existente.setNombreMedicamento(medicamentoDTO.getNombreMedicamento());
        existente.setPrecio(medicamentoDTO.getPrecio());

        Medicamento actualizado = medicamentoRepository.save(existente);
        return medicamentoMapper.toResponseDTO(actualizado);
    }

    @Override
    public void deleteMedicamento(Integer idMedicamento) {
        Medicamento existente = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(
                        "Medicamento con id " + idMedicamento + " no encontrado"));
        medicamentoRepository.delete(existente);
    }

    @Override
    public MedicamentoResponseDTO patchMedicamento(Integer idMedicamento, MedicamentoRequestDTO medicamentoDTO) {
        Medicamento existente = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(
                        "Medicamento con id " + idMedicamento + " no encontrado"));

        if (medicamentoDTO.getNombreMedicamento() != null) {
            existente.setNombreMedicamento(medicamentoDTO.getNombreMedicamento());
        }
        if (medicamentoDTO.getPrecio() != null) {
            existente.setPrecio(medicamentoDTO.getPrecio());
        }

        Medicamento actualizado = medicamentoRepository.save(existente);
        return medicamentoMapper.toResponseDTO(actualizado);
    }
}

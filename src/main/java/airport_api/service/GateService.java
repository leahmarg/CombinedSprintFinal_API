package airport_api.service;

import airport_api.dto.GateDTO;
import airport_api.entity.Gate;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.GateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GateService {

    private final GateRepository gateRepository;

    public GateService(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    // ENTITY TO DTQ
    private GateDTO mapToDTO(Gate gate) {
        GateDTO dto = new GateDTO();

        dto.setId(gate.getId());
        dto.setGateNumber(gate.getGateNumber());

        if (gate.getAirport() != null) {
            dto.setAirportId(gate.getAirport().getId());
            dto.setAirportName(gate.getAirport().getAirportName());
        }

        return dto;
    }

    // VALIDATION
    private void validateGate(Gate gate) {
        if (gate.getGateNumber() == null || gate.getGateNumber().isBlank()) {
            throw new ResourceNotFoundException("Gate number is required");
        }

        if (gate.getAirport() == null) {
            throw new ResourceNotFoundException("Airport is required for a gate");
        }
    }

    // CREATE
    public GateDTO createGate(Gate gate) {
        validateGate(gate);

        Gate saved = gateRepository.save(gate);
        return mapToDTO(saved);
    }

    // GET ALL
    public List<GateDTO> getAllGates() {
        return gateRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public GateDTO getGateById(Long id) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate not found"));

        return mapToDTO(gate);
    }

    // UPDATE
    public GateDTO updateGate(Long id, Gate updatedGate) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate not found"));

        if (updatedGate.getGateNumber() != null) {
            gate.setGateNumber(updatedGate.getGateNumber());
        }

        if (updatedGate.getAirport() != null) {
            gate.setAirport(updatedGate.getAirport());
        }

        Gate saved = gateRepository.save(gate);
        return mapToDTO(saved);
    }

    // DELETE
    public void deleteGate(Long id)  {
        gateRepository.deleteById(id);
    }
}

package airport_api.service;

import airport_api.dto.AircraftDTO;
import airport_api.entity.Aircraft;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.AircraftRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    // ENTITY TO DTO
    private AircraftDTO mapToDTO(Aircraft aircraft) {
        AircraftDTO dto = new AircraftDTO();

        dto.setId(aircraft.getId());
        dto.setAircraftModel(aircraft.getAircraftModel());
        dto.setAircraftCapacity(aircraft.getAircraftCapacity());

        return dto;
    }

    // VALIDATION
    private void validateAircraft(Aircraft aircraft) {
        if (aircraft.getAircraftModel() == null || aircraft.getAircraftModel().isBlank()) {
            throw new IllegalArgumentException("Aircraft model is required");
        }

        if (aircraft.getAircraftCapacity() == null) {
            throw new IllegalArgumentException("Aircraft capacity is required");
        }
    }

    // CREATE
    public AircraftDTO createAircraft(Aircraft aircraft) {
        Aircraft saved = aircraftRepository.save(aircraft);
        return mapToDTO(saved);
    }

    // GET ALL
    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));

        return mapToDTO(aircraft);
    }

    // UPDATE
    public AircraftDTO updateAircraft(Long id, Aircraft updatedAircraft) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        if (updatedAircraft.getAircraftModel() != null) {
            aircraft.setAircraftModel(updatedAircraft.getAircraftModel());
        }

        if (updatedAircraft.getAircraftCapacity() != null) {
            aircraft.setAircraftCapacity(updatedAircraft.getAircraftCapacity());
        }

        Aircraft saved = aircraftRepository.save(aircraft);
        return mapToDTO(saved);
    }

    // DELETE
    public void deleteAircraft(Long id) {
        aircraftRepository.deleteById(id);
    }
}
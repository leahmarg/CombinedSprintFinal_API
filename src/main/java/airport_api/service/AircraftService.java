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

    private AircraftDTO mapToDTO(Aircraft aircraft) {
        AircraftDTO dto = new AircraftDTO();

        dto.setId(aircraft.getId());
        dto.setAircraftModel(aircraft.getAircraftModel());
        dto.setAircraftCapacity(aircraft.getAircraftCapacity());

        return dto;
    }

    private void validateAircraft(Aircraft aircraft) {
        if (aircraft.getAircraftModel() == null || aircraft.getAircraftModel().isBlank()) {
            throw new ResourceNotFoundException("Aircraft model is required");
        }

        if (aircraft.getAircraftCapacity() == null) {
            throw new ResourceNotFoundException("Aircraft capacity is required");
        }
    }

    public AircraftDTO createAircraft(Aircraft aircraft) {
        validateAircraft(aircraft);

        Aircraft saved = aircraftRepository.save(aircraft);
        return mapToDTO(saved);
    }

    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));

        return mapToDTO(aircraft);
    }

    public AircraftDTO updateAircraft(Long id, Aircraft updatedAircraft) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));

        if (updatedAircraft.getAircraftModel() != null) {
            aircraft.setAircraftModel(updatedAircraft.getAircraftModel());
        }

        if (updatedAircraft.getAircraftCapacity() != null) {
            aircraft.setAircraftCapacity(updatedAircraft.getAircraftCapacity());
        }

        Aircraft saved = aircraftRepository.save(aircraft);
        return mapToDTO(saved);
    }

    public void deleteAircraft(Long id) {
        aircraftRepository.deleteById(id);
    }
}
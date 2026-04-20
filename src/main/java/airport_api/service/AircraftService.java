package airport_api.service;

import airport_api.dto.AircraftDTO;
import airport_api.entity.Aircraft;
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

    // Entity to DTO
    private AircraftDTO mapToDTO(Aircraft aircraft) {
        AircraftDTO dto = new AircraftDTO();

        dto.setId(aircraft.getId());
        dto.setAircraftModel(aircraft.getAircraftModel());
        dto.setAircraftCapacity(aircraft.getAircraftCapacity());

        return dto;
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
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        return mapToDTO(aircraft);
    }

    // UPDATE
    public AircraftDTO updateAircraft(Long id, Aircraft updatedAircraft) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        aircraft.setAircraftModel(updatedAircraft.getAircraftModel());
        aircraft.setAircraftCapacity(updatedAircraft.getAircraftCapacity());

        return mapToDTO(aircraftRepository.save(aircraft));
    }

    // DELETE
    public void deleteAircraft(Long id) {
        aircraftRepository.deleteById(id);
    }
}
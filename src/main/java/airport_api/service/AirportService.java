package airport_api.service;

import airport_api.dto.AirportDTO;
import airport_api.entity.Airport;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    private AirportDTO mapToDTO(Airport airport) {
        AirportDTO dto = new AirportDTO();

        dto.setId(airport.getId());
        dto.setAirportName(airport.getAirportName());
        dto.setAirportCode(airport.getAirportCode());
        dto.setCountry(airport.getCountry());
        dto.setCity(airport.getCity());

        return dto;
    }

    private void validateAirport(Airport airport) {
        if (airport.getAirportName() == null || airport.getAirportName().isBlank()) {
            throw new ResourceNotFoundException("Airport name is required");
        }

        if (airport.getAirportCode() == null || airport.getAirportCode().isBlank()) {
            throw new ResourceNotFoundException("Airport code is required");
        }
    }

    public AirportDTO createAirport(Airport airport) {
        validateAirport(airport);

        Airport saved = airportRepository.save(airport);
        return mapToDTO(saved);
    }

    public List<AirportDTO> getAllAirports() {
        return airportRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AirportDTO getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));

        return mapToDTO(airport);
    }

    public AirportDTO updateAirport(Long id, Airport updatedAirport) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));

        if (updatedAirport.getAirportName() != null) {
            airport.setAirportName(updatedAirport.getAirportName());
        }

        if (updatedAirport.getAirportCode() != null) {
            airport.setAirportCode(updatedAirport.getAirportCode());
        }

        if (updatedAirport.getCountry() != null) {
            airport.setCountry(updatedAirport.getCountry());
        }

        if (updatedAirport.getCity() != null) {
            airport.setCity(updatedAirport.getCity());
        }

        Airport saved = airportRepository.save(airport);
        return mapToDTO(saved);
    }

    public void deleteAirport(Long id) {
        airportRepository.deleteById(id);
    }
}

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

    // ENTITY TO DTO
    private AirportDTO mapToDTO(Airport airport) {
        AirportDTO dto = new AirportDTO();

        dto.setId(airport.getId());
        dto.setAirportName(airport.getAirportName());
        dto.setAirportCode(airport.getAirportCode());
        dto.setCountry(airport.getCountry());
        dto.setCity(airport.getCity());

        // NULL CHECKS
        if (airport.getAirportName() != null) {
            dto.setAirportName(airport.getAirportName());
        }

        return dto;
    }

    // CREATE
    public AirportDTO createAirport(Airport airport) {
        Airport saved = airportRepository.save(airport);
        return mapToDTO(saved);
    }

    // GET ALL
    public List<AirportDTO> getAllAirports() {
        return airportRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public AirportDTO getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));

        return mapToDTO(airport);
    }

    // UPDATE
    public AirportDTO updateAirport(Long id, Airport updatedAirport) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));

        airport.setAirportName(updatedAirport.getAirportName());
        airport.setAirportCode(updatedAirport.getAirportCode());
        airport.setCountry(updatedAirport.getCountry());
        airport.setCity(updatedAirport.getCity());

        Airport saved = airportRepository.save(airport);

        return mapToDTO(saved);
    }

    // DELETE
    public void deleteAirport(Long id) {
        airportRepository.deleteById(id);
    }
}

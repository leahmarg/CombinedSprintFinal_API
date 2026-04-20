package airport_api.service;

import airport_api.dto.AirlineDTO;
import airport_api.entity.Airline;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.AirlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    // ENTITY TO DTO
    private AirlineDTO mapToDTO(Airline airline) {
        AirlineDTO dto = new AirlineDTO();

        dto.setId(airline.getId());
        dto.setAirlineName(airline.getAirlineName());
        dto.setAirlineAbrev(airline.getAirlineAbrev());
        dto.setCountry(airline.getCountry());

        return dto;
    }

    // VALIDATION
    private void validateAirline(Airline airline) {
        if (airline.getAirlineName() == null || airline.getAirlineName().isBlank()) {
            throw new IllegalArgumentException("Airline name is required");
        }

        if (airline.getAirlineAbrev() == null || airline.getAirlineAbrev().isBlank()) {
            throw new IllegalArgumentException("Airline abbreviation is required");
        }
    }

    // CREATE
    public AirlineDTO createAirline(Airline airline) {
        validateAirline(airline);

        Airline saved = airlineRepository.save(airline);
        return mapToDTO(saved);
    }

    // GET ALL
    public List<AirlineDTO> getAllAirlines(Airline airline) {
        return airlineRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public AirlineDTO getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        return mapToDTO(airline);
    }

    // UPDATE
    public AirlineDTO updateAirline(Long id, Airline updatedAirline) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        if (updatedAirline.getAirlineName() != null) {
            airline.setAirlineName(updatedAirline.getAirlineName());
        }

        if (updatedAirline.getAirlineAbrev() != null) {
            airline.setAirlineAbrev(updatedAirline.getAirlineAbrev());
        }

        if (updatedAirline.getCountry() != null) {
            airline.setCountry(updatedAirline.getCountry());
        }

        Airline saved = airlineRepository.save(airline);
        return mapToDTO(saved);
    }

    // DELETE
    public void deleteAirline(Long id) {
        airlineRepository.deleteById(id);
    }
}

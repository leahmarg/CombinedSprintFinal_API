package airport_api.service;

import airport_api.dto.FlightDTO;
import airport_api.entity.Flight;
import airport_api.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // ENtity to DTO
    private FlightDTO mapToDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();

        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureTime(flight.getDepartureTime());

        dto.setDepartureAirportName(
                flight.getDepartureAirport().getName()
        );

        dto.setArrivalAirportName(
                flight.getArrivalAirport().getName()
        );

        dto.setAircraftModel(
                flight.getAircraft().getModel()
        );

        return dto;
    }

    // CREATE
    public FlightDTO createFlight(Flight flight) {
        Flight saved = flightRepository.save(flight);
        return mapToDTO(saved);
    }

    // GET ALL
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        return mapToDTO(flight);
    }

    // UPDATE
    public FlightDTO updateFlight(Long id, Flight updatedFlight) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setFlightNumber(updatedFlight.getFlightNumber());
        flight.setDepartureTime(updatedFlight.getDepartureTime());
        flight.setDepartureAirport(updatedFlight.getDepartureAirport());
        flight.setArrivalAirport(updatedFlight.getArrivalAirport());
        flight.setAircraft(updatedFlight.getAircraft());

        return mapToDTO(flightRepository.save(flight));
    }

    // DELETE
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
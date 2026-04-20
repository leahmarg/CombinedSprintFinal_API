package airport_api.service;

import airport_api.dto.FlightDTO;
import airport_api.entity.*;
import airport_api.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final GateRepository gateRepository;

    public FlightService(
            FlightRepository flightRepository,
            AirlineRepository airlineRepository,
            AirportRepository airportRepository,
            AircraftRepository aircraftRepository,
            GateRepository gateRepository
    ) {
        this.flightRepository = flightRepository;
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.aircraftRepository = aircraftRepository;
        this.gateRepository = gateRepository;
    }

    // Entity to DTO
    private FlightDTO mapToDTO(Flight flight) {

        FlightDTO dto = new FlightDTO();

        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureTime(flight.getDepartureTime());

        // Null checks
        if (flight.getDepartureAirport() != null) {
            dto.setDepartureAirportCode(flight.getDepartureAirport().getAirportCode());
        }

        if (flight.getArrivalAirport() != null) {
            dto.setArrivalAirportCode(flight.getArrivalAirport().getAirportCode());
        }

        if (flight.getAircraft() != null) {
            dto.setAircraftModel(flight.getAircraft().getAircraftModel());
        }

        if (flight.getAirline() != null) {
            dto.setAirlineName(flight.getAirline().getAirlineName());
        }

        if (flight.getGate() != null) {
            dto.setGateNumber(flight.getGate().getGateNumber());
        }

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
        flight.setAirline(updatedFlight.getAirline());
        flight.setGate(updatedFlight.getGate());

        Flight saved = flightRepository.save(flight);

        return mapToDTO(saved);
    }

    // DELETE
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
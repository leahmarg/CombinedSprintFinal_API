package airport_api.service;

import airport_api.dto.FlightDTO;
import airport_api.entity.*;
import airport_api.exception.ResourceNotFoundException;
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

        // PLACEHOLDER ATM
        dto.setStatus("SCHEDULED");

        return dto;
    }

    // VALIDATION
    private void validateFlight(Flight flight) {

        if (flight.getFlightNumber() == null || flight.getFlightNumber().isBlank()) {
            throw new ResourceNotFoundException("Flight number is required");
        }

        if (flight.getDepartureTime() == null) {
            throw new ResourceNotFoundException("Departure time is required");
        }

        if (flight.getDepartureAirport() == null) {
            throw new ResourceNotFoundException("Departure airport is required");
        }

        if (flight.getArrivalAirport() == null) {
            throw new ResourceNotFoundException("Arrival airport is required");
        }

        if (flight.getAircraft() == null) {
            throw new ResourceNotFoundException("Aircraft is required");
        }

        if (flight.getAirline() == null) {
            throw new ResourceNotFoundException("Airline is required");
        }

        if (flight.getGate() == null) {
            throw new ResourceNotFoundException("Gate is required");
        }
    }

    // CREATE
    public FlightDTO createFlight(Flight flight) {

        validateFlight(flight);

        Flight saved = flightRepository.save(flight);

        Flight fullFlight = flightRepository.findById(saved.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        return mapToDTO(fullFlight);
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
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        return mapToDTO(flight);
    }

    // UPDATE
    public FlightDTO updateFlight(Long id, Flight updatedFlight) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        if (updatedFlight.getFlightNumber() != null) {
            flight.setFlightNumber(updatedFlight.getFlightNumber());
        }

        if (updatedFlight.getDepartureTime() != null) {
            flight.setDepartureTime(updatedFlight.getDepartureTime());
        }

        if (updatedFlight.getDepartureAirport() != null) {
            flight.setDepartureAirport(updatedFlight.getDepartureAirport());
        }

        if (updatedFlight.getArrivalAirport() != null) {
            flight.setArrivalAirport(updatedFlight.getArrivalAirport());
        }

        if (updatedFlight.getAircraft() != null) {
            flight.setAircraft(updatedFlight.getAircraft());
        }

        if (updatedFlight.getAirline() != null) {
            flight.setAirline(updatedFlight.getAirline());
        }

        if (updatedFlight.getGate() != null) {
            flight.setGate(updatedFlight.getGate());
        }

        Flight saved = flightRepository.save(flight);

        return mapToDTO(saved);
    }

    // DELETE
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    // ARRIVAL AND DEPARTURE ENDPOINTS
    public List<FlightDTO> getFlightsByDepartureAirport(Long airportId) {
        return flightRepository.findByDepartureAirportId(airportId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO> getFlightsByArrivalAirport(Long airportId) {
        return flightRepository.findByArrivalAirportId(airportId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
package airport_api.service;

import airport_api.dto.FlightDTO;
import airport_api.dto.FlightRequestDTO;
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

    private FlightDTO mapToDTO(Flight flight) {

        FlightDTO dto = new FlightDTO();

        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureTime(flight.getDepartureTime());

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

        if (flight.getStatus() != null) {
            dto.setStatus(flight.getStatus().name());
        }

        return dto;
    }

    private void validateFlightRequest(FlightRequestDTO dto) {

        if (dto.getFlightNumber() == null || dto.getFlightNumber().isBlank()) {
            throw new ResourceNotFoundException("Flight number is required");
        }

        if (dto.getDepartureTime() == null) {
            throw new ResourceNotFoundException("Departure time is required");
        }

        if (dto.getDepartureAirportId() == null) {
            throw new ResourceNotFoundException("Departure airport is required");
        }

        if (dto.getArrivalAirportId() == null) {
            throw new ResourceNotFoundException("Arrival airport is required");
        }

        if (dto.getAircraftId() == null) {
            throw new ResourceNotFoundException("Aircraft is required");
        }

        if (dto.getAirlineId() == null) {
            throw new ResourceNotFoundException("Airline is required");
        }

        if (dto.getGateId() == null) {
            throw new ResourceNotFoundException("Gate is required");
        }

        if (dto.getStatus() != null) {
            try {
                FlightStatus.valueOf(dto.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Invalid flight status");
            }
        }
    }

    public FlightDTO createFlight(FlightRequestDTO dto) {

        validateFlightRequest(dto);

        Flight flight = new Flight();

        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureTime(dto.getDepartureTime());

        flight.setDepartureAirport(
                airportRepository.findById(dto.getDepartureAirportId())
                        .orElseThrow(() -> new ResourceNotFoundException("Departure airport not found"))
        );

        flight.setArrivalAirport(
                airportRepository.findById(dto.getArrivalAirportId())
                        .orElseThrow(() -> new ResourceNotFoundException("Arrival airport not found"))
        );

        flight.setAircraft(
                aircraftRepository.findById(dto.getAircraftId())
                        .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"))
        );

        flight.setAirline(
                airlineRepository.findById(dto.getAirlineId())
                        .orElseThrow(() -> new ResourceNotFoundException("Airline not found"))
        );

        flight.setGate(
                gateRepository.findById(dto.getGateId())
                        .orElseThrow(() -> new ResourceNotFoundException("Gate not found"))
        );

        flight.setStatus(
                dto.getStatus() != null
                        ? FlightStatus.valueOf(dto.getStatus().toUpperCase())
                        : FlightStatus.SCHEDULED
        );

        Flight saved = flightRepository.save(flight);

        return mapToDTO(saved);
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        return mapToDTO(flight);
    }

    public FlightDTO updateFlight(Long id, FlightRequestDTO dto) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        if (dto.getFlightNumber() != null) {
            flight.setFlightNumber(dto.getFlightNumber());
        }

        if (dto.getDepartureTime() != null) {
            flight.setDepartureTime(dto.getDepartureTime());
        }

        if (dto.getDepartureAirportId() != null) {
            flight.setDepartureAirport(
                    airportRepository.findById(dto.getDepartureAirportId())
                            .orElseThrow(() -> new ResourceNotFoundException("Departure airport not found"))
            );
        }

        if (dto.getArrivalAirportId() != null) {
            flight.setArrivalAirport(
                    airportRepository.findById(dto.getArrivalAirportId())
                            .orElseThrow(() -> new ResourceNotFoundException("Arrival airport not found"))
            );
        }

        if (dto.getAircraftId() != null) {
            flight.setAircraft(
                    aircraftRepository.findById(dto.getAircraftId())
                            .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"))
            );
        }

        if (dto.getAirlineId() != null) {
            flight.setAirline(
                    airlineRepository.findById(dto.getAirlineId())
                            .orElseThrow(() -> new ResourceNotFoundException("Airline not found"))
            );
        }

        if (dto.getGateId() != null) {
            flight.setGate(
                    gateRepository.findById(dto.getGateId())
                            .orElseThrow(() -> new ResourceNotFoundException("Gate not found"))
            );
        }

        if (dto.getStatus() != null) {
            flight.setStatus(FlightStatus.valueOf(dto.getStatus().toUpperCase()));
        }

        Flight saved = flightRepository.save(flight);

        return mapToDTO(saved);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

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
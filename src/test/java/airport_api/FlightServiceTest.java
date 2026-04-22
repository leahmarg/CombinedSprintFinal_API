package airport_api;

import airport_api.dto.FlightRequestDTO;
import airport_api.entity.*;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.*;
import airport_api.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    private FlightRepository flightRepository;
    private AirlineRepository airlineRepository;
    private AirportRepository airportRepository;
    private AircraftRepository aircraftRepository;
    private GateRepository gateRepository;

    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightRepository = mock(FlightRepository.class);
        airlineRepository = mock(AirlineRepository.class);
        airportRepository = mock(AirportRepository.class);
        aircraftRepository = mock(AircraftRepository.class);
        gateRepository = mock(GateRepository.class);

        flightService = new FlightService(
                flightRepository,
                airlineRepository,
                airportRepository,
                aircraftRepository,
                gateRepository
        );
    }

    @Test
    void shouldCreateFlight() {

        FlightRequestDTO dto = new FlightRequestDTO();
        dto.setFlightNumber("AC123");
        dto.setDepartureTime(LocalDateTime.now());
        dto.setDepartureAirportId(1L);
        dto.setArrivalAirportId(2L);
        dto.setAircraftId(1L);
        dto.setAirlineId(1L);
        dto.setGateId(1L);
        dto.setStatus("SCHEDULED");

        Airport airport1 = new Airport();
        airport1.setId(1L);
        airport1.setAirportCode("YYZ");

        Airport airport2 = new Airport();
        airport2.setId(2L);
        airport2.setAirportCode("JFK");

        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);
        aircraft.setAircraftModel("Boeing 737");

        Airline airline = new Airline();
        airline.setId(1L);
        airline.setAirlineName("Air Canada");

        Gate gate = new Gate();
        gate.setId(1L);
        gate.setGateNumber("A1");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport1));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(airport2));
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        when(gateRepository.findById(1L)).thenReturn(Optional.of(gate));

        Flight savedFlight = new Flight();
        savedFlight.setId(100L);
        savedFlight.setFlightNumber("AC123");
        savedFlight.setDepartureTime(dto.getDepartureTime());
        savedFlight.setDepartureAirport(airport1);
        savedFlight.setArrivalAirport(airport2);
        savedFlight.setAircraft(aircraft);
        savedFlight.setAirline(airline);
        savedFlight.setGate(gate);
        savedFlight.setStatus(FlightStatus.SCHEDULED);

        when(flightRepository.save(any(Flight.class))).thenReturn(savedFlight);

        var result = flightService.createFlight(dto);

        assertNotNull(result);
        assertEquals("AC123", result.getFlightNumber());
        assertEquals("YYZ", result.getDepartureAirportCode());
        assertEquals("JFK", result.getArrivalAirportCode());
        assertEquals("SCHEDULED", result.getStatus());

        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void shouldGetFlightById() {

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AC123");

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        var result = flightService.getFlightById(1L);

        assertEquals("AC123", result.getFlightNumber());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateFlight() {

        FlightRequestDTO dto = new FlightRequestDTO();
        dto.setFlightNumber("AC999");
        dto.setDepartureTime(LocalDateTime.now());

        dto.setDepartureAirportId(1L);
        dto.setArrivalAirportId(2L);
        dto.setAircraftId(1L);
        dto.setAirlineId(1L);
        dto.setGateId(1L);
        dto.setStatus("DEPARTED");

        Flight existingFlight = new Flight();
        existingFlight.setId(1L);
        existingFlight.setFlightNumber("OLD123");

        when(flightRepository.findById(1L)).thenReturn(Optional.of(existingFlight));

        Airport airport1 = new Airport();
        airport1.setId(1L);
        airport1.setAirportCode("YYZ");

        Airport airport2 = new Airport();
        airport2.setId(2L);
        airport2.setAirportCode("JFK");

        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);
        aircraft.setAircraftModel("Boeing 737");

        Airline airline = new Airline();
        airline.setId(1L);
        airline.setAirlineName("Air Canada");

        Gate gate = new Gate();
        gate.setId(1L);
        gate.setGateNumber("A1");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport1));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(airport2));
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        when(gateRepository.findById(1L)).thenReturn(Optional.of(gate));

        Flight updatedFlight = new Flight();
        updatedFlight.setId(1L);
        updatedFlight.setFlightNumber("AC999");
        updatedFlight.setDepartureTime(dto.getDepartureTime());
        updatedFlight.setDepartureAirport(airport1);
        updatedFlight.setArrivalAirport(airport2);
        updatedFlight.setAircraft(aircraft);
        updatedFlight.setAirline(airline);
        updatedFlight.setGate(gate);
        updatedFlight.setStatus(FlightStatus.DEPARTED);

        when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);

        var result = flightService.updateFlight(1L, dto);

        assertNotNull(result);
        assertEquals("AC999", result.getFlightNumber());
        assertEquals("DEPARTED", result.getStatus());
        assertEquals("YYZ", result.getDepartureAirportCode());
        assertEquals("JFK", result.getArrivalAirportCode());

        verify(flightRepository, times(1)).findById(1L);
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void shouldThrowWhenFlightNotFound() {

        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> flightService.getFlightById(1L));
    }

    @Test
    void shouldDeleteFlight() {

        doNothing().when(flightRepository).deleteById(1L);

        flightService.deleteFlight(1L);

        verify(flightRepository, times(1)).deleteById(1L);
    }
}
package airport_api;

import airport_api.dto.FlightRequestDTO;
import airport_api.entity.*;
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

        // ===== DTO INPUT =====
        FlightRequestDTO dto = new FlightRequestDTO();
        dto.setFlightNumber("AC123");
        dto.setDepartureTime(LocalDateTime.now());
        dto.setDepartureAirportId(1L);
        dto.setArrivalAirportId(2L);
        dto.setAircraftId(1L);
        dto.setAirlineId(1L);
        dto.setGateId(1L);

        // ===== MOCK ENTITIES =====
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

        // ===== MOCK REPOSITORY LOOKUPS =====
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport1));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(airport2));
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        when(gateRepository.findById(1L)).thenReturn(Optional.of(gate));

        // ===== MOCK SAVE =====
        Flight savedFlight = new Flight();
        savedFlight.setId(100L);
        savedFlight.setFlightNumber("AC123");
        savedFlight.setDepartureTime(dto.getDepartureTime());
        savedFlight.setDepartureAirport(airport1);
        savedFlight.setArrivalAirport(airport2);
        savedFlight.setAircraft(aircraft);
        savedFlight.setAirline(airline);
        savedFlight.setGate(gate);

        when(flightRepository.save(any(Flight.class))).thenReturn(savedFlight);

        // ===== EXECUTE =====
        var result = flightService.createFlight(dto);

        // ===== ASSERT =====
        assertNotNull(result);
        assertEquals("AC123", result.getFlightNumber());
        assertEquals("YYZ", result.getDepartureAirportCode());
        assertEquals("JFK", result.getArrivalAirportCode());

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
    }

    @Test
    void shouldThrowWhenFlightNotFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            flightService.getFlightById(1L);
        });
    }

    @Test
    void shouldDeleteFlight() {
        doNothing().when(flightRepository).deleteById(1L);

        flightService.deleteFlight(1L);

        verify(flightRepository, times(1)).deleteById(1L);
    }
}
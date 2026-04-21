package airport_api;

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
        Airport airport1 = new Airport();
        airport1.setId(1L);
        airport1.setAirportCode("YYZ");

        Airport airport2 = new Airport();
        airport2.setId(2L);
        airport2.setAirportCode("JFK");

        Aircraft aircraft1 = new Aircraft();
        aircraft1.setId(1L);
        aircraft1.setAircraftModel("Boeing 737");

        Airline airline1 = new Airline();
        airline1.setId(1L);
        airline1.setAirlineName("Air Canada");

        Gate gate1 = new Gate();
        gate1.setId(1L);
        gate1.setGateNumber("A1");

        Flight flight = new Flight();
        flight.setFlightNumber("AC123");
        flight.setDepartureTime(LocalDateTime.now());
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setAircraft(aircraft1);
        flight.setAirline(airline1);
        flight.setGate(gate1);

        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        var result = flightService.createFlight(flight);

        assertNotNull(result);
        assertEquals("AC123", result.getFlightNumber());

        verify(flightRepository, times(1)).save(flight);
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
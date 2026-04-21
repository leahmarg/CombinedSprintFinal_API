package airport_api;

import airport_api.entity.Airport;
import airport_api.repository.AirportRepository;
import airport_api.exception.ResourceNotFoundException;
import airport_api.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AirportServiceTest {

    private AirportRepository airportRepository;
    private AirportService airportService;

    @BeforeEach
    void setUp() {
        airportRepository = mock(AirportRepository.class);
        airportService = new AirportService(airportRepository);
    }

    @Test
    void shouldCreateAirport() {
        Airport airport1 = new Airport();
        airport1.setAirportName("St. John's International Airport");
        airport1.setAirportCode("YYT");

        when(airportRepository.save(any(Airport.class))).thenReturn(airport1);

        var result = airportService.createAirport(airport1);

        verify(airportRepository, times(1)).save(airport1);
    }

    @Test
    void shouldGetById() {
        Airport airport1 = new Airport();
        airport1.setAirportName("St. John's International Airport");
        airport1.setAirportCode("YYT");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport1));

        var result = airportService.getAirportById(1L);

        assertEquals("YYT", result.getAirportCode());
    }

    @Test
    void shouldThrowWhenFlightNotFound() {
        when(airportRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            airportService.getAirportById(1L);
        });
    }

    @Test
    void shouldDeleteFlight() {
        doNothing().when(airportRepository).deleteById(1L);

        airportService.deleteAirport(1L);

        verify(airportRepository, times(1)).deleteById(1L);
    }
}

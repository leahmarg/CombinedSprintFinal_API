package airport_api;

import airport_api.dto.AirlineDTO;
import airport_api.entity.Airline;
import airport_api.repository.AirlineRepository;
import airport_api.service.AirlineService;
import airport_api.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AirlineServiceTest {

    private AirlineRepository airlineRepository;
    private AirlineService airlineService;

    @BeforeEach
    void setUp() {
        airlineRepository = mock(AirlineRepository.class);
        airlineService = new AirlineService(airlineRepository);
    }

    @Test
    void shouldCreateAirport() {
        Airline airline1 = new Airline();
        airline1.setAirlineName("Air Canada");
        airline1.setAirlineCode("AC");
        airline1.setCountry("Canada");

        when(airlineRepository.save(any(Airline.class))).thenReturn(airline1);

        var result = airlineService.createAirline(airline1);

        verify(airlineRepository, times(1)).save(airline1);
    }

    @Test
    void shouldGetAirlineById() {
        Airline airline = new Airline();
        airline.setId(1L);

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));

        assertNotNull(airlineService.getAirlineById(1L));
    }

    @Test
    void shouldUpdateAirline() {

        Airline existing = new Airline();
        existing.setId(1L);
        existing.setAirlineName("Old Airline");

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(existing));

        Airline updated = new Airline();
        updated.setId(1L);
        updated.setAirlineName("New Airline");

        when(airlineRepository.save(any(Airline.class))).thenReturn(updated);

        AirlineDTO result = airlineService.updateAirline(1L, updated);

        assertNotNull(result);
        assertEquals("New Airline", result.getAirlineName());

        verify(airlineRepository, times(1)).findById(1L);
        verify(airlineRepository, times(1)).save(any(Airline.class));
    }

    @Test
    void shouldThrowWhenAirlineNotFound() {
        when(airlineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            airlineService.getAirlineById(1L);
        });
    }

    @Test
    void shouldDeleteAirline() {
        doNothing().when(airlineRepository).deleteById(1L);

        airlineService.deleteAirline(1L);

        verify(airlineRepository, times(1)).deleteById(1L);
    }
}

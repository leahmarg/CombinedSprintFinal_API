package airport_api;

import airport_api.entity.Aircraft;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.AircraftRepository;
import airport_api.service.AircraftService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AircraftServiceTest {

    private AircraftRepository aircraftRepository;
    private AircraftService aircraftService;

    @BeforeEach
    void setUp() {
        aircraftRepository = mock(AircraftRepository.class);
        aircraftService = new AircraftService(aircraftRepository);
    }

    @Test
    void shouldCreateAircraft() {
        Aircraft aircraft1 = new Aircraft();
        aircraft1.setAircraftModel("Boeing");
        aircraft1.setAircraftCapacity(100L);

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft1));

        var result = aircraftService.getAircraftById(1L);

        assertEquals("Boeing", result.getAircraftModel());
    }

    @Test
    void shouldGetAircraftById() {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));

        assertNotNull(aircraftService.getAircraftById(1L));
    }

    @Test
    void shouldThrowWhenAircraftNotFound() {
        when(aircraftRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            aircraftService.getAircraftById(1L);
        });
    }

    @Test
    void shouldDeleteAircraft() {
        doNothing().when(aircraftRepository).deleteById(1L);

        aircraftService.deleteAircraft(1L);

        verify(aircraftRepository, times(1)).deleteById(1L);
    }
}

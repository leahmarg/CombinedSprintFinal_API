package airport_api;

import airport_api.dto.GateDTO;
import airport_api.entity.Airport;
import airport_api.entity.Gate;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.GateRepository;
import airport_api.service.GateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GateServiceTest {

    private GateRepository gateRepository;
    private GateService gateService;

    @BeforeEach
    void setUp() {
        gateRepository = mock(GateRepository.class);
        gateService = new GateService(gateRepository);
    }

    @Test
    void shouldCreateGate() {
        Airport airport1 = new Airport();
        airport1.setAirportName("St. John's International Airport");
        airport1.setAirportCode("YYT");

        Gate gate = new Gate();
        gate.setGateNumber("A1");
        gate.setAirport(airport1);

        when(gateRepository.save(any(Gate.class))).thenReturn(gate);

        var result = gateService.createGate(gate);

        assertEquals("A1", result.getGateNumber());
    }

    @Test
    void shouldUpdateGate() {

        Gate existing = new Gate();
        existing.setId(1L);
        existing.setGateNumber("A1");

        when(gateRepository.findById(1L)).thenReturn(Optional.of(existing));

        Gate updated = new Gate();
        updated.setId(1L);
        updated.setGateNumber("B2");

        when(gateRepository.save(any(Gate.class))).thenReturn(updated);

        GateDTO result = gateService.updateGate(1L, updated);

        assertNotNull(result);
        assertEquals("B2", result.getGateNumber());

        verify(gateRepository, times(1)).findById(1L);
        verify(gateRepository, times(1)).save(any(Gate.class));
    }

    @Test
    void shouldThrowWhenGateNotFound() {
        when(gateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            gateService.getGateById(1L);
        });
    }

    @Test
    void shouldDeleteFlight() {
        doNothing().when(gateRepository).deleteById(1L);

        gateService.deleteGate(1L);

        verify(gateRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldDeleteGate() {
        doNothing().when(gateRepository).deleteById(1L);

        gateService.deleteGate(1L);

        verify(gateRepository, times(1)).deleteById(1L);
    }
}

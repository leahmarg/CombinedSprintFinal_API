package airport_api;

import airport_api.dto.PassengerDTO;
import airport_api.entity.Passenger;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.PassengerRepository;
import airport_api.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PassengerServiceTest {

    private PassengerRepository passengerRepository;
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        passengerRepository = mock(PassengerRepository.class);
        passengerService = new PassengerService(passengerRepository);
    }

    @Test
    void shouldCreatePassenger() {
        Passenger passenger1 = new Passenger();
        passenger1.setFirstName("John");
        passenger1.setLastName("Bob");
        passenger1.setEmail("johnbob@example.com");
        passenger1.setPhone("1234567890");
        passenger1.setPassportCode("123ASD");

        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger1);

        var result = passengerService.createPassenger(passenger1);

        assertEquals("John", result.getFirstName());
    }

    @Test
    void shouldGetPassengerById() {
        Passenger passenger1 = new Passenger();
        passenger1.setId(1L);

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger1));

        assertNotNull(passengerService.getPassengerById(1L));
    }

    @Test
    void shouldUpdatePassenger() {

        Passenger existingPassenger = new Passenger();
        existingPassenger.setId(1L);
        existingPassenger.setFirstName("John");
        existingPassenger.setLastName("Bob");
        existingPassenger.setEmail("johnbob@example.com");
        existingPassenger.setPhone("1234567890");
        existingPassenger.setPassportCode("123ASD");

        Passenger updatedInput = new Passenger();
        updatedInput.setFirstName("Jane");
        updatedInput.setLastName("Smith");
        updatedInput.setEmail("janesmith@example.com");
        updatedInput.setPhone("9999999999");
        updatedInput.setPassportCode("999XYZ");

        Passenger savedPassenger = new Passenger();
        savedPassenger.setId(1L);
        savedPassenger.setFirstName("Jane");
        savedPassenger.setLastName("Smith");
        savedPassenger.setEmail("janesmith@example.com");
        savedPassenger.setPhone("9999999999");
        savedPassenger.setPassportCode("999XYZ");

        when(passengerRepository.findById(1L))
                .thenReturn(Optional.of(existingPassenger));

        when(passengerRepository.save(any(Passenger.class)))
                .thenReturn(savedPassenger);

        PassengerDTO result = passengerService.updatePassenger(1L, updatedInput);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("janesmith@example.com", result.getEmail());
        assertEquals("9999999999", result.getPhone());
        assertEquals("999XYZ", result.getPassportCode());

        verify(passengerRepository, times(1)).findById(1L);
        verify(passengerRepository, times(1)).save(any(Passenger.class));
    }

    @Test
    void shouldThrowWhenPassengerNotFound() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.getPassengerById(1L);
        });
    }

    @Test
    void shouldDeletePassenger() {
        doNothing().when(passengerRepository).deleteById(1L);

        passengerService.deletePassenger(1L);

        verify(passengerRepository, times(1)).deleteById(1L);
    }
}

package airport_api.service;

import airport_api.dto.PassengerDTO;
import airport_api.entity.Passenger;
import airport_api.exception.ResourceNotFoundException;
import airport_api.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // ENTITY TO DTO
    private PassengerDTO mapToDTO(Passenger passenger) {

        PassengerDTO dto = new PassengerDTO();

        dto.setId(passenger.getId());
        dto.setFirstName(passenger.getFirstName());
        dto.setLastName(passenger.getLastName());
        dto.setEmail(passenger.getEmail());
        dto.setPhone(passenger.getPhone());
        dto.setPassportCode(passenger.getPassportCode());

        if (passenger.getFlight() != null) {
            dto.setFlightId(passenger.getFlight().getId());
            dto.setFlightNumber(passenger.getFlight().getFlightNumber());
        }

        return dto;
    }

    // VALIDATION
    private void validatePassenger(Passenger passenger) {
        if (passenger.getFirstName() == null || passenger.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (passenger.getLastName() == null || passenger.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (passenger.getPassportCode() == null || passenger.getPassportCode().isBlank()) {
            throw new IllegalArgumentException("Passport code is required");
        }
    }

    // CREATE
    public PassengerDTO createPassenger(Passenger passenger) {
        validatePassenger(passenger);

        Passenger saved = passengerRepository.save(passenger);
        return mapToDTO(saved);
    }

    // GET ALL
    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public PassengerDTO getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        return mapToDTO(passenger);
    }

    // UPDATE
    public PassengerDTO updatePassenger(Long id, Passenger updated) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        if (updated.getFirstName() != null) {
            passenger.setFirstName(updated.getFirstName());
        }

        if (updated.getLastName() != null) {
            passenger.setLastName(updated.getLastName());
        }

        if (updated.getEmail() != null) {
            passenger.setEmail(updated.getEmail());
        }

        if (updated.getPhone() != null) {
            passenger.setPhone(updated.getPhone());
        }

        if (updated.getPassportCode() != null) {
            passenger.setPassportCode(updated.getPassportCode());
        }

        if (updated.getFlight() != null) {
            passenger.setFlight(updated.getFlight());
        }

        return mapToDTO(passengerRepository.save(passenger));
    }

    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
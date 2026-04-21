package airport_api.controller;

import airport_api.dto.PassengerDTO;
import airport_api.entity.Passenger;
import airport_api.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody Passenger passenger) {
        return ResponseEntity.ok(passengerService.createPassenger(passenger));
    }

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(
            @PathVariable Long id,
            @RequestBody Passenger passenger
    ) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passenger));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
package airport_api.controller;

import airport_api.dto.FlightDTO;
import airport_api.dto.FlightRequestDTO;
import airport_api.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(
            @Valid @RequestBody FlightRequestDTO dto
    ) {
        return ResponseEntity.ok(flightService.createFlight(dto));
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequestDTO dto
    ) {
        return ResponseEntity.ok(flightService.updateFlight(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    // ARRIVALS AND DEPARTURES
    @GetMapping("/departures/{airportId}")
    public ResponseEntity<List<FlightDTO>> getDepartures(@PathVariable Long airportId) {
        return ResponseEntity.ok(flightService.getFlightsByDepartureAirport(airportId));
    }

    @GetMapping("/arrivals/{airportId}")
    public ResponseEntity<List<FlightDTO>> getArrivals(@PathVariable Long airportId) {
        return ResponseEntity.ok(flightService.getFlightsByArrivalAirport(airportId));
    }
}
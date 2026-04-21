package airport_api.controller;

import airport_api.dto.AirlineDTO;
import airport_api.entity.Airline;
import airport_api.service.AirlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<AirlineDTO> createAirline(@RequestBody Airline airline) {
        return ResponseEntity.ok(airlineService.createAirline(airline));
    }

    @GetMapping
    public ResponseEntity<List<AirlineDTO>> getAllAirlines() {
        return ResponseEntity.ok(airlineService.getAllAirlines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineDTO> getAirlineById(@PathVariable Long id) {
        return ResponseEntity.ok(airlineService.getAirlineById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineDTO> updateAirline(
            @PathVariable Long id,
            @RequestBody Airline airline
    ) {
        return ResponseEntity.ok(airlineService.updateAirline(id, airline));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirline(@PathVariable Long id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.noContent().build();
    }
}
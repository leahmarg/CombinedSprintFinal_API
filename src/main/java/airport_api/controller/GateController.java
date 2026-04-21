package airport_api.controller;

import airport_api.dto.GateDTO;
import airport_api.entity.Gate;
import airport_api.service.GateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gates")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @PostMapping
    public ResponseEntity<GateDTO> createGate(@Valid @RequestBody Gate gate) {
        return ResponseEntity.ok(gateService.createGate(gate));
    }

    @GetMapping
    public ResponseEntity<List<GateDTO>> getAllGates() {
        return ResponseEntity.ok(gateService.getAllGates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GateDTO> getGateById(@PathVariable Long id) {
        return ResponseEntity.ok(gateService.getGateById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GateDTO> updateGate(
            @PathVariable Long id,
            @Valid @RequestBody Gate gate
    ) {
        return ResponseEntity.ok(gateService.updateGate(id, gate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGate(@PathVariable Long id) {
        gateService.deleteGate(id);
        return ResponseEntity.noContent().build();
    }
}
package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AircraftDTO {

    private Long id;

    @NotBlank(message = "Model is required")
    private String aircraftModel;

    @NotBlank(message = "Capacity is required")
    private Long aircraftCapacity;
}
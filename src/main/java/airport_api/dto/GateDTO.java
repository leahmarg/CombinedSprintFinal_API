package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GateDTO {

    private Long id;

    @NotBlank(message = "Gate number is required")
    private String gateNumber;

    @NotBlank(message = "Gate ID is required")
    private Long airportId;

    @NotBlank(message = "Airport name is required")
    private String airportName;
}
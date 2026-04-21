package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GateDTO {

    private Long id;

    @NotBlank(message = "Gate number is required")
    private String gateNumber;

    @NotBlank(message = "Gate ID is required")
    private Long airportId;

    private String airportName;
}
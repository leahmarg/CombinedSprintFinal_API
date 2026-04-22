package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirlineDTO {

    private Long id;

    @NotBlank(message = "Airline name is required")
    private String airlineName;

    @NotBlank(message = "Airline abbreviation is required")
    private String airlineCode;

    @NotBlank(message = "Country is required")
    private String country;
}
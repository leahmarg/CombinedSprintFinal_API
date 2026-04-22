package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirportDTO {

    private Long id;

    @NotBlank(message = "Airport name is required")
    private String airportName;

    @NotBlank(message = "Airport code is required")
    private String airportCode;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "City is required")
    private String city;
}
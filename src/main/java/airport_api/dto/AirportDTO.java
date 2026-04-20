package airport_api.dto;

import lombok.Data;

@Data
public class AirportDTO {

    private Long id;
    private String airportName;
    private String airportCode;
    private String country;
    private String city;
}
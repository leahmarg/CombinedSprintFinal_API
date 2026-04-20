package airport_api.dto;

import lombok.Data;

@Data
public class AirlineDTO {

    private Long id;
    private String airlineName;
    private String airlineAbrev;
    private String country;
}
package airport_api.dto;

import lombok.Data;

@Data
public class AircraftDTO {

    private Long id;
    private String aircraftModel;
    private Long aircraftCapacity;
}
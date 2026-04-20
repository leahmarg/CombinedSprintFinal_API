package airport_api.dto;

import lombok.Data;

@Data
public class GateDTO {

    private Long id;
    private String gateNumber;
    private Long airportId;
    private String airportName;
}
package airport_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightDTO {

    private Long id;
    private String flightNumber;
    private LocalDateTime departureTime;

    private String departureAirportCode;
    private String arrivalAirportCode;
    private String aircraftModel;

    private Long airlineId;
    private String airlineName;
    private String gateNumber;
    private String status;
}
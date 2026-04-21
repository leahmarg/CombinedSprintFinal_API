package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightDTO {

    private Long id;

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotBlank(message = "Departure airport code is required")
    private String departureAirportCode;

    @NotBlank(message = "Arrival airport code is required")
    private String arrivalAirportCode;

    @NotBlank(message = "Aircraft model is required")
    private String aircraftModel;

    @NotBlank(message = "Airline name is required")
    private String airlineName;

    @NotBlank(message = "Gate number is required")
    private String gateNumber;

    @NotBlank(message = "Status is required")
    private String status;
}
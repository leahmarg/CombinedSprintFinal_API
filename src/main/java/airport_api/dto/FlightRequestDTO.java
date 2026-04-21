package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequestDTO {

    @NotBlank
    private String flightNumber;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private Long departureAirportId;

    @NotNull
    private Long arrivalAirportId;

    @NotNull
    private Long aircraftId;

    @NotNull
    private Long airlineId;

    @NotNull
    private Long gateId;
}
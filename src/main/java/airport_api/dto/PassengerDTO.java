package airport_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PassengerDTO {

    private Long id;

    @NotBlank(message = "Passenger name is required")
    private String firstName;

    @NotBlank(message = "Passenger name is required")
    private String lastName;

    private String email;

    @NotBlank(message = "Passenger phone number is required")
    private String phone;

    @NotBlank(message = "Passport code is required")
    private String passportCode;

    @NotBlank(message = "Flight id is required")
    private Long flightId;

    private String flightNumber;
}
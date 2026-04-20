package airport_api.dto;

import lombok.Data;

@Data
public class PassengerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String passportCode;

    private Long flightNumber;
}
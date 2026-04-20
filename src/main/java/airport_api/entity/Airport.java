package airport_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airportName;
    private String airportCode;
    private String country;
    private String city;

    //  Many flights departing from this airport
    @OneToMany(mappedBy = "departureAirport")
    @JsonIgnore
    private List<Flight> departingFlights;

    // Many flights arriving at this airport
    @OneToMany(mappedBy = "arrivalAirport")
    @JsonIgnore
    private List<Flight> arrivingFlights;

    // One airport has many gates
    @OneToMany(mappedBy = "airport")
    @JsonIgnore
    private List<Gate> gates;
}
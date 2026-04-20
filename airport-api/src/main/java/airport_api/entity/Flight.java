package airport_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private LocalDateTime departureTime;

    // Many flights can depart from one airport
    @ManyToOne
    private Airport departureAirport;

    // Many flights can arrive at one airport
    @ManyToOne
    private Airport arrivalAirport;

    // Many flights use one aircraft
    @ManyToOne
    private Aircraft aircraft;

    // One flight has many passengers
    @OneToMany(mappedBy = "flight")
    @JsonIgnore
    private List<Passenger> passengers;

    // Airline operating this flight
    @ManyToOne
    private Airline airline;

    // Many flights can be assigned to one gate
    @ManyToOne
    private Gate gate;
}
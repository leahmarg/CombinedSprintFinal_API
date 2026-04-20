package airport_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airlineName;
    private String airlineAbrev;
    private String country;

    // One airline operates many flights
    @OneToMany(mappedBy = "airline")
    private List<Flight> flights;
}
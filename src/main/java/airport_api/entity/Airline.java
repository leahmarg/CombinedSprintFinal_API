package airport_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airlineName;
    private String airlineCode;
    private String country;

    // One airline operates many flights
    @OneToMany(mappedBy = "airline")
    @JsonIgnore
    private List<Flight> flights;
}
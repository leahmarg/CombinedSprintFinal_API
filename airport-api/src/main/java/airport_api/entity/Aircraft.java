package airport_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aircraftModel;
    private Long aircraftCapacity;

    // One aircraft can be used for many flights
    @OneToMany(mappedBy = "aircraft")
    @JsonIgnore
    private List<Flight> flights;
}
package airport_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gateNumber;

    // Many gates belong to one airport
    @ManyToOne
    private Airport airport;

    // One gate can be used by many flights over time
    @OneToMany(mappedBy = "gate")
    @JsonIgnore
    private List<Flight> flights;
}
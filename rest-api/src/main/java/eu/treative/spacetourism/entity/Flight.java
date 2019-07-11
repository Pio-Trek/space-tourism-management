package eu.treative.spacetourism.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private Integer numberOfSeats;

    private Double ticketPrice;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE})
    @JoinTable(name = "tourist_flights",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "tourist_id"))
    private Set<Tourist> tourists = new HashSet<>();

    public Flight(LocalDateTime departure, LocalDateTime arrival, int numberOfSeats, Double ticketPrice) {
        this.departure = departure;
        this.arrival = arrival;
        this.numberOfSeats = numberOfSeats;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", numberOfSeats=" + numberOfSeats +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}

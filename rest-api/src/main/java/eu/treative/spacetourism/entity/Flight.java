package eu.treative.spacetourism.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private LocalDateTime departure;

    @NotNull
    private LocalDateTime arrival;

    @NotNull
    @Min(value = 1, message = "The number of seats must be positive")
    private Integer numberOfSeats;

    @NotNull
    @Min(value = 1, message = "The ticket price must be positive")
    private Double ticketPrice;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE})
    @JoinTable(name = "tourist_flights",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "tourist_id"))
    private Set<Tourist> tourists = new HashSet<>();

    public Flight(LocalDateTime departure, LocalDateTime arrival, Integer numberOfSeats, Double ticketPrice) {
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

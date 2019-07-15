package eu.treative.spacetourism.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    private Long id;

    @NotNull
    @FutureOrPresent
    private LocalDateTime departure;

    @NotNull
    @FutureOrPresent
    private LocalDateTime arrival;

    @NotNull
    @Min(value = 1, message = "The number of seats must be positive")
    private Integer numberOfSeats;

    @NotNull
    @Min(value = 1, message = "The ticket price must be positive")
    private Double ticketPrice;

    private Set<Tourist> tourists = new HashSet<>();

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

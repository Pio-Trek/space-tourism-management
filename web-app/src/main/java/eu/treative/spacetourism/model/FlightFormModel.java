package eu.treative.spacetourism.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightFormModel {

    private Long id;

    @NotNull
    private String departure;

    @NotNull
    private String arrival;

    @NotNull
    @Min(value = 1, message = "The number of seats must be positive")
    private Integer numberOfSeats;

    @NotNull
    @Min(value = 1, message = "The ticket price must be positive")
    private Double ticketPrice;

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

package eu.treative.spacetourism.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class FlightDTO {

    private Long id;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private Integer numberOfSeats;

    private Double ticketPrice;

    private Set<TouristDTO> tourists = new HashSet<>();
}

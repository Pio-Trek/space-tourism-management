package eu.treative.spacetourism.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class FlightDTO {

    private Long id;

    private Date departure;

    private Date arrival;

    private Integer numberOfSeats;

    private BigDecimal ticketPrice;

    private Set<TouristDTO> tourists = new HashSet<>();
}

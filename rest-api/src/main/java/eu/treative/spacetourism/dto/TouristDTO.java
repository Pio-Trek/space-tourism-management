package eu.treative.spacetourism.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TouristDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String country;

    private String remarks;

    private LocalDate dob;

    private Set<FlightDTO> flights = new HashSet<>();
}

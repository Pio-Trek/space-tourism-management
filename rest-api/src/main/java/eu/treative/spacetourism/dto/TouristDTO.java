package eu.treative.spacetourism.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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

    private Date dob;

    private Set<FlightDTO> flights = new HashSet<>();
}

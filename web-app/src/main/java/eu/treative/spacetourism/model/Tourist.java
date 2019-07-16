package eu.treative.spacetourism.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tourist {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Gender gender;

    @NotBlank
    private String country;

    private String remarks;

    @NotNull
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private Set<Flight> flights = new HashSet<>();

    @Override
    public String toString() {
        return "Tourist{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", country='" + country + '\'' +
                ", remarks='" + remarks + '\'' +
                ", dob=" + dob +
                '}';
    }
}

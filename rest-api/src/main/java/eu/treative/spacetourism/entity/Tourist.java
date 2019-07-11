package eu.treative.spacetourism.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.treative.spacetourism.dto.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    private String country;

    @Lob
    private String remarks;

    @NotNull
    @PastOrPresent
    private LocalDate dob;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE})
    @JoinTable(name = "tourist_flights",
            joinColumns = @JoinColumn(name = "tourist_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private Set<Flight> flights = new HashSet<>();

    public Tourist(@NotBlank String firstName, @NotBlank String lastName, Gender gender, String country, String remarks, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
        this.remarks = remarks;
        this.dob = dob;
    }

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

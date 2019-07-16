package eu.treative.spacetourism.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TouristFromModel {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Gender gender;

    @NotBlank
    private String country;

    private String remarks;

    @NotBlank
    private String dob;

    @Override
    public String toString() {
        return "TouristFromModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", country='" + country + '\'' +
                ", remarks='" + remarks + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}

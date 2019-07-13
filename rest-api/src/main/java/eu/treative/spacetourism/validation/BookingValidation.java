package eu.treative.spacetourism.validation;

import eu.treative.spacetourism.entity.Flight;

public interface BookingValidation {

    boolean isEnoughSeats(Flight flight);

}

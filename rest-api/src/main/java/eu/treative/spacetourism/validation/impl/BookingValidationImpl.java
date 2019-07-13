package eu.treative.spacetourism.validation.impl;

import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.validation.BookingValidation;

public class BookingValidationImpl implements BookingValidation {

    @Override
    public boolean isEnoughSeats(Flight flight) {
        int numberOfSeats = flight.getNumberOfSeats();
        int numberOfReservations = flight.getTourists().size();
        return numberOfSeats < numberOfReservations;
    }

}

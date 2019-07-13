package eu.treative.spacetourism.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class OutOfSeatsException extends RuntimeException {

    private final Long flightId;

    public OutOfSeatsException(Long flightId) {
        super(String.format("Flight with ID: '%s' is fully booked.", flightId));
        this.flightId = flightId;
    }

}

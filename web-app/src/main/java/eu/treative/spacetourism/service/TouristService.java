package eu.treative.spacetourism.service;

import eu.treative.spacetourism.model.Tourist;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface TouristService {

    List<Tourist> getAllTourists();

    Tourist getTourist(Long id);

    Tourist addTourist(Tourist flight);

    Tourist updateTourist(Tourist flight, Long id);

    HttpStatus removeTourist(Long id);

}

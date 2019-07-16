package eu.treative.spacetourism.dao;

import eu.treative.spacetourism.model.Tourist;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface TouristDAO {

    List<Tourist> getAllTourists();

    Tourist getTourist(Long id);

    Tourist addTourist(Tourist tourist);

    Tourist updateTourist(Tourist tourist, Long id);

    HttpStatus removeTourist(Long id);
}

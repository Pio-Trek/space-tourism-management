package eu.treative.spacetourism.dao;

import eu.treative.spacetourism.model.Tourist;

import java.util.List;

public interface TouristDAO {

    List<Tourist> getAllTourists();

    Tourist getTourist(Long id);

    Tourist addTourist(Tourist tourist);

    Tourist updateTourist(Tourist tourist, Long id);

    boolean removeTourist(Long id);
}

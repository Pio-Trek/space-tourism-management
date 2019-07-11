package eu.treative.spacetourism.service;

import eu.treative.spacetourism.model.Tourist;

import java.util.List;

public interface TouristService {

    List<Tourist> getAllTourists();

    Tourist getTourist(Long id);

    Tourist addTourist(Tourist tourist);

    Tourist updateTourist(Tourist tourist, Long id);

    void removeTourist(Long id);


}

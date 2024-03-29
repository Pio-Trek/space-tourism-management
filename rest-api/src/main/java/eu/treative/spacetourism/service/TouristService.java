package eu.treative.spacetourism.service;

import eu.treative.spacetourism.entity.Tourist;

import java.util.List;

public interface TouristService {

    List<Tourist> getAllTourists();

    Tourist getTourist(Long id);

    Tourist addTourist(Tourist tourist);

    Tourist updateTouristDetails(Tourist tourist, Long id);

    void removeTourist(Long id);


}

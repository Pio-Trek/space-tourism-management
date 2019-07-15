package eu.treative.spacetourism.service;

import eu.treative.spacetourism.model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    void removeFlight(Long id);
}

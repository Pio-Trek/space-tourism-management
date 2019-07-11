package eu.treative.spacetourism.service;

import eu.treative.spacetourism.entity.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    Flight getFlight(Long id);

    Flight addFlight(Flight tourist);

    Flight updateFlight(Flight tourist, Long id);

    void removeFlight(Long id);
}

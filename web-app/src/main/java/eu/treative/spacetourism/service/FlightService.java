package eu.treative.spacetourism.service;

import eu.treative.spacetourism.model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    Flight getFlight(Long id);

    Flight addFlight(Flight flight);

    Flight updateFlight(Flight flight, Long id);

    void removeFlight(Long id);

}

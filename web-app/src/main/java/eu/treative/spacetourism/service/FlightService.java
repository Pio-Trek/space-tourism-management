package eu.treative.spacetourism.service;

import eu.treative.spacetourism.model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    Flight getFlight(Long id);

    Flight addFlight(Flight flight);

    Flight updateFlight(Flight flight, Long id);

    Flight removeTouristFromFlight(Long touristId, Long flightId);

    boolean removeFlight(Long id);

}

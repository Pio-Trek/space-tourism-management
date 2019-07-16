package eu.treative.spacetourism.dao;

import eu.treative.spacetourism.model.Flight;

import java.util.List;

public interface FlightDAO {

    List<Flight> getAllFlights();

    Flight getFlight(Long id);

    Flight addFlight(Flight flight);

    Flight updateFlight(Flight flight, Long id);

    void removeFight(Long id);

}

package eu.treative.spacetourism.dao;

import eu.treative.spacetourism.model.Flight;

import java.util.List;

public interface FlightDAO {

    List<Flight> getAllFlights();

    Flight getFlight(Long id);

    Flight addFlight(Flight flight);

    Flight updateFlight(Flight flight, Long id);

    Flight addTouristToFlight(Long touristId, Long flightId);

    Flight removeTouristFromFlight(Long touristId, Long flightId);

    boolean removeFight(Long id);

}

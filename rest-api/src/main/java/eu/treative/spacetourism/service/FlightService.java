package eu.treative.spacetourism.service;

import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.entity.Tourist;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    Flight getFlight(Long id);

    Flight addFlight(Flight tourist);

    Flight updateFlightDetails(Flight tourist, Long id);

    Flight addTouristToFlight(Tourist tourist, Long flightId);

    void removeFlight(Long id);
}

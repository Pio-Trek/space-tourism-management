package eu.treative.spacetourism.service;

import eu.treative.spacetourism.entity.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();

    Flight getFlight(Long id);

    List<Flight> getFlightsByTouristsId(Long id);

    Flight addFlight(Flight tourist);

    Flight updateFlightDetails(Flight tourist, Long id);

    Flight addTouristToFlight(Long flightId, Long touristId);

    Flight removeTouristFromFlight(Long touristId, Long flightId);

    void removeFlight(Long id);

}

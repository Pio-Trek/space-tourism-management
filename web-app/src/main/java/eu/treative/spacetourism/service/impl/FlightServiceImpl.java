package eu.treative.spacetourism.service.impl;

import eu.treative.spacetourism.dao.FlightDAO;
import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightDAO flightDAO;

    @Autowired
    public FlightServiceImpl(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public List<Flight> getAllFlights() {
        log.info("Getting all flights");
        return flightDAO.getAllFlights();
    }

    @Override
    public Flight getFlight(Long id) {
        Flight flight = flightDAO.getFlight(id);
        log.info("Getting flight with id: {}", id);
        return flight;
    }

    @Override
    public Flight addFlight(Flight flight) {
        Flight savedFlight = flightDAO.addFlight(flight);
        log.info("Saving {}", savedFlight);
        return savedFlight;
    }

    @Override
    public Flight updateFlight(Flight flight, Long id) {
        Flight updatedFlight = flightDAO.updateFlight(flight, id);
        log.info("Updating {}", updatedFlight);
        return updatedFlight;
    }


    @Override
    public boolean removeFlight(Long id) {
        log.info("Deleting flight with id: {}", id);
        return flightDAO.removeFight(id);
    }
}

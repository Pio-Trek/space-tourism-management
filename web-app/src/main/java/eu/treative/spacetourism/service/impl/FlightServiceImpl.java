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
    public void removeFlight(Long id) {
        log.info("Deleting flight with id: {}", id);
        flightDAO.removeFight(id);
    }
}

package eu.treative.spacetourism.service.impl;

import eu.treative.spacetourism.exception.ResourceNotFoundException;
import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.repository.FlightRepository;
import eu.treative.spacetourism.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository repository;

    @Autowired
    public FlightServiceImpl(FlightRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Flight> getAllFlights() {
        log.info("Getting all flights");
        return repository.findAll();
    }

    @Override
    public Flight getFlight(Long id) {
        log.info("Getting flight with id: {}", id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
    }

    @Override
    public Flight addFlight(Flight flight) {
        Flight savedFlight = repository.save(flight);
        log.info("Saving {}", savedFlight);
        return savedFlight;
    }

    @Override
    public Flight updateFlight(Flight newFlight, Long id) {
        if (repository.existsById(id)) {
            newFlight.setId(id);
            Flight updatedFlight = repository.save(newFlight);
            log.info("Updating {}", updatedFlight);
            return updatedFlight;
        } else {
            throw new ResourceNotFoundException("Flight", "id", id);
        }
    }

    @Override
    public void removeFlight(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Deleting flight with id: {}", id);
        } else {
            log.info("Flight to delete with id {} is not found", id);
            throw new ResourceNotFoundException("Flight", "id", id);
        }
    }
}

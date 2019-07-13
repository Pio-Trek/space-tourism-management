package eu.treative.spacetourism.service.impl;

import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.entity.Tourist;
import eu.treative.spacetourism.exception.OutOfSeatsException;
import eu.treative.spacetourism.exception.ResourceNotFoundException;
import eu.treative.spacetourism.repository.FlightRepository;
import eu.treative.spacetourism.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public Flight updateFlightDetails(Flight newFlight, Long id) {
        if (repository.existsById(id)) {
            Set<Tourist> tourists = repository.findById(id).get().getTourists();
            newFlight.setId(id);
            newFlight.setTourists(tourists);
            Flight updatedFlight = repository.save(newFlight);
            log.info("Updating {}", updatedFlight);
            return updatedFlight;
        } else {
            throw new ResourceNotFoundException("Flight", "id", id);
        }
    }

    @Override
    public Flight addTouristToFlight(Tourist tourist, Long flightId) {
        Flight flight = repository.findById(flightId).orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId));

        int numberOfSeats = flight.getNumberOfSeats();
        int numberOfReservations = flight.getTourists().size();
        if (numberOfSeats > numberOfReservations) {
            Set<Tourist> tourists = flight.getTourists();
            tourists.add(tourist);
            flight.setTourists(tourists);
            return repository.save(flight);
        } else {
            throw new OutOfSeatsException(flightId);
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

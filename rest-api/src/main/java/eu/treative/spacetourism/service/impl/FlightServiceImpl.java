package eu.treative.spacetourism.service.impl;

import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.entity.Tourist;
import eu.treative.spacetourism.exception.OutOfSeatsException;
import eu.treative.spacetourism.exception.ResourceNotFoundException;
import eu.treative.spacetourism.repository.FlightRepository;
import eu.treative.spacetourism.repository.TouristRepository;
import eu.treative.spacetourism.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final TouristRepository touristRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository repository, TouristRepository touristRepository) {
        this.flightRepository = repository;
        this.touristRepository = touristRepository;
    }

    @Override
    public List<Flight> getAllFlights() {
        log.info("Getting all flights");
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> getFlightsByTouristsId(Long id) {
        log.info("Getting all flights by tourist id: {}", id);
        return flightRepository.findFlightsByTouristsId(id);
    }

    @Override
    public Flight getFlight(Long id) {
        log.info("Getting flight with id: {}", id);
        return flightRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
    }

    @Override
    public Flight addFlight(Flight flight) {
        Flight savedFlight = flightRepository.save(flight);
        log.info("Saving {}", savedFlight);
        return savedFlight;
    }

    @Override
    public Flight updateFlightDetails(Flight newFlight, Long id) {
        if (flightRepository.existsById(id)) {
            Set<Tourist> tourists = flightRepository.findById(id).get().getTourists();
            newFlight.setId(id);
            newFlight.setTourists(tourists);
            Flight updatedFlight = flightRepository.save(newFlight);
            log.info("Updating {}", updatedFlight);
            return updatedFlight;
        } else {
            throw new ResourceNotFoundException("Flight", "id", id);
        }
    }

    @Override
    public Flight addTouristToFlight(Long flightId, Long touristId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId));
        Tourist tourist = touristRepository.findById(touristId).orElseThrow(() -> new ResourceNotFoundException("Tourist", "id", flightId));

        int numberOfSeats = flight.getNumberOfSeats();
        int numberOfReservations = flight.getTourists().size();
        if (numberOfSeats > numberOfReservations) {
            Set<Tourist> tourists = flight.getTourists();
            tourists.add(tourist);
            flight.setTourists(tourists);
            log.info("Adding Tourist with ID {} to the Flight with ID {}", tourist.getId(), flightId);
            return flightRepository.save(flight);
        } else {
            throw new OutOfSeatsException(flightId);
        }

    }

    @Override
    public Flight removeTouristFromFlight(Long touristId, Long flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId));

        Set<Tourist> tourists = flight.getTourists();
        boolean isRemoved = tourists.removeIf(t -> t.getId().equals(touristId));

        if (isRemoved) {
            flight.setTourists(tourists);
            log.info("Removing Tourist with ID {} from the Flight with ID {}", touristId, flightId);
            return flightRepository.save(flight);
        } else {
            throw new ResourceNotFoundException("Tourist : ID " + touristId, "Flight", flightId);
        }
    }

    @Override
    public void removeFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            log.info("Deleting flight with id: {}", id);
        } else {
            log.info("Flight to delete with id {} is not found", id);
            throw new ResourceNotFoundException("Flight", "id", id);
        }
    }

}

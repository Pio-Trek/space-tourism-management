package eu.treative.spacetourism.service.impl;

import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.entity.Tourist;
import eu.treative.spacetourism.exception.ResourceNotFoundException;
import eu.treative.spacetourism.repository.TouristRepository;
import eu.treative.spacetourism.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class TouristServiceImpl implements TouristService {

    private final TouristRepository repository;

    @Autowired
    public TouristServiceImpl(TouristRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tourist> getAllTourists() {
        log.info("Getting all tourists");
        return repository.findAll();
    }

    @Override
    public Tourist getTourist(Long id) {
        log.info("Getting tourist with id: {}", id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tourist", "id", id));
    }

    @Override
    public Tourist addTourist(Tourist tourist) {
        Tourist savedTourist = repository.save(tourist);
        log.info("Saving {}", savedTourist);
        return savedTourist;
    }

    @Override
    public Tourist updateTouristDetails(Tourist newTourist, Long id) {
        if (repository.existsById(id)) {
            Set<Flight> flights = repository.findById(id).get().getFlights();
            newTourist.setId(id);
            newTourist.setFlights(flights);
            Tourist updatedTourist = repository.save(newTourist);
            log.info("Updating {}", updatedTourist);
            return updatedTourist;
        } else {
            throw new ResourceNotFoundException("Tourist", "id", id);
        }
    }

    @Override
    public void removeTourist(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Deleting tourist with id: {}", id);
        } else {
            log.info("Tourist to delete with id {} is not found", id);
            throw new ResourceNotFoundException("Tourist", "id", id);
        }
    }
}

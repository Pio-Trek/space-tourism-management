package eu.treative.spacetourism.service.impl;

import eu.treative.spacetourism.dao.TouristDAO;
import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TouristServiceImpl implements TouristService {

    private final TouristDAO touristDAO;

    @Autowired
    public TouristServiceImpl(TouristDAO touristDAO) {
        this.touristDAO = touristDAO;
    }

    @Override
    public List<Tourist> getAllTourists() {
        log.info("Getting all tourists");
        return touristDAO.getAllTourists();
    }

    @Override
    public Tourist getTourist(Long id) {
        Tourist tourist = touristDAO.getTourist(id);
        log.info("Getting tourist with id: {}", id);
        return tourist;
    }

    @Override
    public Tourist addTourist(Tourist flight) {
        Tourist savedTourist = touristDAO.addTourist(flight);
        log.info("Saving {}", savedTourist);
        return savedTourist;
    }

    @Override
    public Tourist updateTourist(Tourist flight, Long id) {
        return null;
    }

    @Override
    public boolean removeTourist(Long id) {
        log.info("Deleting tourist with id: {}", id);
        return touristDAO.removeTourist(id);
    }
}

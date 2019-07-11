package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.dto.TouristDTO;
import eu.treative.spacetourism.entity.Tourist;
import eu.treative.spacetourism.service.TouristService;
import eu.treative.spacetourism.utils.URLContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URLContants.API_TOURIST)
public class TouristController {

    private final TouristService service;

    @Autowired
    public TouristController(TouristService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tourist> getAllTourists() {
        return service.getAllTourists();
    }

    @GetMapping("/{id}")
    public Tourist getTourist(@PathVariable Long id) {
        return service.getTourist(id);
    }

    @PostMapping
    public Tourist createTourist(@RequestBody TouristDTO touristDTO) {
        ModelMapper mapper = new ModelMapper();
        Tourist tourist = mapper.map(touristDTO, Tourist.class);
        return service.addTourist(tourist);
    }

    @PutMapping("/{id}")
    public Tourist updateTourist(@RequestBody TouristDTO touristDTO, @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        Tourist tourist = mapper.map(touristDTO, Tourist.class);
        return service.updateTourist(tourist, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTourist(@PathVariable Long id) {
        service.removeTourist(id);
    }
}

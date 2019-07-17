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

    private final TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public List<Tourist> getAllTourists() {
        return touristService.getAllTourists();
    }

    @GetMapping("/{id}")
    public Tourist getTourist(@PathVariable Long id) {
        return touristService.getTourist(id);
    }

    @PostMapping
    public Tourist createTourist(@RequestBody TouristDTO touristDTO) {
        ModelMapper mapper = new ModelMapper();
        Tourist tourist = mapper.map(touristDTO, Tourist.class);
        return touristService.addTourist(tourist);
    }

    @PutMapping("/{id}")
    public Tourist updateTouristDetails(@RequestBody TouristDTO touristDTO, @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        Tourist tourist = mapper.map(touristDTO, Tourist.class);
        return touristService.updateTouristDetails(tourist, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTourist(@PathVariable Long id) {
        touristService.removeTourist(id);
    }
}

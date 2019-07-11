package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.dto.FlightDTO;
import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.utils.URLContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(URLContants.API_FLIGHT)
public class FlightController {

    private final FlightService service;

    @Autowired
    public FlightController(FlightService service) {
        this.service = service;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return service.getAllFlights();
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return service.getFlight(id);
    }

    @PostMapping
    public Flight createFlight(@RequestBody @Valid FlightDTO flightDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Flight flight = modelMapper.map(flightDTO, Flight.class);
        return service.addFlight(flight);
    }

    @PutMapping("/{id}")
    public Flight updateFlight(@RequestBody @Valid FlightDTO flightDTO, @PathVariable Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Flight flight = modelMapper.map(flightDTO, Flight.class);
        return service.updateFlight(flight, id);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        service.removeFlight(id);
    }
}

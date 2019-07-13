package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.dto.FlightDTO;
import eu.treative.spacetourism.dto.TouristDTO;
import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.entity.Tourist;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.utils.URLContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Flight createFlight(@RequestBody FlightDTO flightDTO) {
        ModelMapper mapper = new ModelMapper();
        Flight flight = mapper.map(flightDTO, Flight.class);
        return service.addFlight(flight);
    }

    @PutMapping("/{id}")
    public Flight updateFlightDetails(@RequestBody FlightDTO flightDTO, @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        Flight flight = mapper.map(flightDTO, Flight.class);
        return service.updateFlightDetails(flight, id);
    }

    @PutMapping("/{flightId}/tourist")
    public Flight addTouristToFlight(@RequestBody TouristDTO touristDTO, @PathVariable Long flightId) {
        ModelMapper mapper = new ModelMapper();
        Tourist tourist = mapper.map(touristDTO, Tourist.class);
        return service.addTouristToFlight(tourist, flightId);
    }

    @DeleteMapping("/{flightId}/tourist/{touristId}")
    public Flight removeTouristFromFlight(@PathVariable Long flightId, @PathVariable Long touristId) {
        return service.removeTouristFromFlight(touristId, flightId);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        service.removeFlight(id);
    }
}

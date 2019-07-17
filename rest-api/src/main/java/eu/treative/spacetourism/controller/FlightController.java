package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.dto.FlightDTO;
import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.utils.URLContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URLContants.API_FLIGHT)
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.getFlight(id);
    }

    @GetMapping("/tourist/{id}")
    public List<Flight> getFlightsByTouristsId(@PathVariable Long id) {
        return flightService.getFlightsByTouristsId(id);
    }

    @PostMapping
    public Flight createFlight(@RequestBody FlightDTO flightDTO) {
        ModelMapper mapper = new ModelMapper();
        Flight flight = mapper.map(flightDTO, Flight.class);
        return flightService.addFlight(flight);
    }

    @PutMapping("/{id}")
    public Flight updateFlightDetails(@RequestBody FlightDTO flightDTO, @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        Flight flight = mapper.map(flightDTO, Flight.class);
        return flightService.updateFlightDetails(flight, id);
    }

    @PutMapping("/{flightId}/tourist/{touristId}")
    public Flight addTouristToFlight(@PathVariable Long flightId, @PathVariable Long touristId) {
        return flightService.addTouristToFlight(flightId, touristId);
    }

    @DeleteMapping("/{flightId}/tourist/{touristId}")
    public Flight removeTouristFromFlight(@PathVariable Long flightId, @PathVariable Long touristId) {
        return flightService.removeTouristFromFlight(touristId, flightId);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.removeFlight(id);
    }
}

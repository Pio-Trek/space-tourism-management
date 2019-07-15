package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.utils.URLContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(URLContants.URL_FLIGHT)
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String flightsList(Model model) {
        List<Flight> allFlights = flightService.getAllFlights();
        model.addAttribute("flightList", allFlights);
        model.addAttribute("dateN", new Date());
        model.addAttribute("sampleText", "aaa bbb cc");
        return "/flight/index";
    }
}

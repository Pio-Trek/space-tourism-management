package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.utils.URLContants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(URLContants.WEB_FLIGHT)
public class FlightController {

    @GetMapping
    public String flightsList(Model model) {
        return "/flight/index";
    }
}

package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.service.TouristService;
import eu.treative.spacetourism.utils.URLContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(URLContants.URL_TOURIST)
public class TouristController {

    private final TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public String flightsList(@ModelAttribute("message") String message, Model model) {
        List<Tourist> allTourists = touristService.getAllTourists();
        model.addAttribute("touristList", allTourists);
        model.addAttribute("message", message);
        return "/tourist/index";
    }

}

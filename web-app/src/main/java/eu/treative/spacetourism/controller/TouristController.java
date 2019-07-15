package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.utils.URLContants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping(URLContants.WEB_TOURIST)
public class TouristController {

    @GetMapping
    public String demo(Model model) {
        model.addAttribute("date", new Date());
        return "flight/demo";
    }

}

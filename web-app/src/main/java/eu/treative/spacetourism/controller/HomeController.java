package eu.treative.spacetourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("headerMessage", "The City of Hillwood largest missing and found pet database");
        return "home/index";
    }
}

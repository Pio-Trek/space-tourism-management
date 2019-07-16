package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.service.TouristService;
import eu.treative.spacetourism.utils.URLContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{id}/delete")
    public String removeTourist(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        HttpStatus httpStatus = touristService.removeTourist(id);
        if (httpStatus.is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("message", "You successfully removed the tourist.");
            return "redirect:/tourist";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to delete a tourist with ID: " + id);
            return "redirect:/tourist";
        }

    }

}

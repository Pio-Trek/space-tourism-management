package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.model.FlightFormModel;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.utils.URLContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(URLContants.URL_FLIGHT)
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String flightsList(@ModelAttribute("message") String message, Model model) {
        List<Flight> allFlights = flightService.getAllFlights();
        model.addAttribute("flightList", allFlights);
        model.addAttribute("message", message);
        return "/flight/index";
    }

    @GetMapping("/add")
    public String addFlight(@ModelAttribute("errorMessage") String errors, Model model) {
        model.addAttribute("flight", new FlightFormModel());
        model.addAttribute("errors", errors);
        return "flight/add";
    }

    @PostMapping("/add")
    public String addFlight(@Valid @ModelAttribute("flight") FlightFormModel flightFormModel, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        boolean formIsNotValid = false;
        StringBuilder errors = new StringBuilder();

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError error : fieldErrors) {
                errors.append(error.getField()).append(" - wrong value: ").append(error.getRejectedValue()).append(". <br />");
            }

            formIsNotValid = true;
        }

        try {
            LocalDateTime departureDate = LocalDateTime.parse(flightFormModel.getDeparture());
            LocalDateTime arrivalDate = LocalDateTime.parse(flightFormModel.getArrival());

            Flight flight = new Flight();

            // Departure and Arrival date time validation

            if (departureDate.isAfter(LocalDateTime.now())) {
                flight.setDeparture(departureDate);
            } else {
                formIsNotValid = true;
                errors.append("Departure - the date must be future or present. <br />");
            }

            if (arrivalDate.isAfter(LocalDateTime.now())) {
                flight.setArrival(arrivalDate);
            } else {
                formIsNotValid = true;
                errors.append("Arrival - the date must be future or present. <br />");
            }

            if (departureDate.isAfter(arrivalDate)) {
                formIsNotValid = true;
                errors.append("Departure date cannot be after the arrival. <br />");
            }

            if (formIsNotValid) {
                model.addAttribute("errors", errors.toString());
                return "flight/add";
            }


            flight.setNumberOfSeats(flightFormModel.getNumberOfSeats());
            flight.setTicketPrice(flightFormModel.getTicketPrice());
            flightService.addOrUpdateFlight(flight);
            redirectAttributes.addFlashAttribute("message", "You successfully added a new flight.");
        } catch (Exception e) {
            log.error("Error message: {}", e.getMessage());
        }

        return "redirect:/flight";
    }


    @GetMapping("/{id}/delete")
    public String removeFlight(@PathVariable Long id) {
        flightService.removeFlight(id);
        return "redirect:/flight";
    }
}

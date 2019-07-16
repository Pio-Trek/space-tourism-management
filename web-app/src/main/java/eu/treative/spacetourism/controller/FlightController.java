package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.model.FlightFormModel;
import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.service.TouristService;
import eu.treative.spacetourism.utils.Constant;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping(Constant.URL_FLIGHT)
public class FlightController {

    private final FlightService flightService;
    private final TouristService touristService;

    @Autowired
    public FlightController(FlightService flightService, TouristService touristService) {
        this.flightService = flightService;
        this.touristService = touristService;
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
        model.addAttribute("action", Constant.ACTION_ADD);
        model.addAttribute("errors", errors);
        return "flight/add-amend";
    }

    @GetMapping("/{id}/amend")
    public String amendFlight(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("errorMessage") String errors, Model model) {

        Flight flight = flightService.getFlight(id);

        if (flight != null) {
            model.addAttribute("flight", flight);
            model.addAttribute("action", Constant.ACTION_AMEND);
            model.addAttribute("errors", errors);
            return "flight/add-amend";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to amend a flight.");
            return "redirect:/flight";
        }
    }

    @PostMapping("/add-amend")
    public String addOrAmendFlight(@Valid @ModelAttribute("flight") FlightFormModel flightFormModel, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        String action = ((flightFormModel.getId() == null) ? Constant.ACTION_ADD : Constant.ACTION_AMEND);

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
                model.addAttribute("action", action);
                model.addAttribute("errors", errors.toString());
                return "flight/add-amend";
            }

            flight.setNumberOfSeats(flightFormModel.getNumberOfSeats());
            flight.setTicketPrice(flightFormModel.getTicketPrice());

            if (action.equals(Constant.ACTION_AMEND)) {
                flightService.updateFlight(flight, flightFormModel.getId());
            } else {
                flightService.addFlight(flight);
            }
            redirectAttributes.addFlashAttribute("message", "You have successfully " + action + "ed a flight.");
        } catch (Exception e) {
            log.error("Error message: {}", e.getMessage());
        }

        return "redirect:/flight";
    }

    @GetMapping("/{id}/tourist")
    public String flightTouristList(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Flight flight = flightService.getFlight(id);
        Set<Tourist> currentFlightTourists = flight.getTourists();
        Set<Tourist> allOtherTourists = new HashSet<>(touristService.getAllTourists());
        allOtherTourists.removeAll(currentFlightTourists);

        boolean isFlightFull = flight.getNumberOfSeats() <= currentFlightTourists.size();

        model.addAttribute("currentFlightTourists", currentFlightTourists);
        model.addAttribute("allOtherTourists", allOtherTourists);
        model.addAttribute("flightId", id);
        model.addAttribute("isFlightFull", isFlightFull);
        model.addAttribute("message", message);
        return "/flight/tourist-list";
    }

    @PostMapping("{flightId}/tourist/{touristId}/add")
    public String addTouristToFlight(@PathVariable Long flightId, @PathVariable Long touristId, RedirectAttributes redirectAttributes) {
        Flight flight = flightService.addTouristToFlight(touristId, flightId);

        if (flight != null) {
            redirectAttributes.addFlashAttribute("message", "You have successfully added tourist with ID " + touristId);
            return "redirect:/flight/" + flightId + "/tourist";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to add tourist with ID " + touristId + " to the flight.");
            return "redirect:/flight";
        }

    }

    @PostMapping("{flightId}/tourist/{touristId}/delete")
    public String removeTouristFromFlight(@PathVariable Long flightId, @PathVariable Long touristId, RedirectAttributes redirectAttributes) {
        Flight flight = flightService.removeTouristFromFlight(touristId, flightId);

        if (flight != null) {
            redirectAttributes.addFlashAttribute("message", "You have successfully removed tourist with ID " + touristId);
            return "redirect:/flight/" + flightId + "/tourist";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to remove a tourist with ID " + touristId + " from the flight.");
            return "redirect:/flight";
        }

    }

    @GetMapping("/{id}/delete")
    public String removeFlight(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isFlightRemoved = flightService.removeFlight(id);

        if (isFlightRemoved) {
            redirectAttributes.addFlashAttribute("message", "You have successfully removed the flight with ID: " + id);
            return "redirect:/flight";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to delete a flight with ID: " + id);
            return "redirect:/flight";
        }
    }
}
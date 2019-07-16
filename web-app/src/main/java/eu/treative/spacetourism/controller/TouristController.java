package eu.treative.spacetourism.controller;

import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.model.TouristFromModel;
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
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(Constant.URL_TOURIST)
public class TouristController {

    private final TouristService touristService;
    private final FlightService flightService;

    @Autowired
    public TouristController(TouristService touristService, FlightService flightService) {
        this.touristService = touristService;
        this.flightService = flightService;
    }

    @GetMapping
    public String touristsList(@ModelAttribute("message") String message, Model model) {
        List<Tourist> allTourists = touristService.getAllTourists();
        model.addAttribute("touristList", allTourists);
        model.addAttribute("message", message);
        return "/tourist/index";
    }

    @GetMapping("/add")
    public String addTourist(@ModelAttribute("errorMessage") String errors, Model model) {
        model.addAttribute("tourist", new TouristFromModel());
        model.addAttribute("action", Constant.ACTION_ADD);
        model.addAttribute("errors", errors);
        return "tourist/add-amend";
    }

    @GetMapping("/{id}/amend")
    public String amendTourist(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("errorMessage") String errors, Model model) {

        Tourist tourist = touristService.getTourist(id);

        if (tourist != null) {
            model.addAttribute("tourist", tourist);
            model.addAttribute("action", Constant.ACTION_AMEND);
            model.addAttribute("errors", errors);
            return "tourist/add-amend";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to amend a tourist.");
            return "redirect:/tourist";
        }
    }

    @PostMapping("/add-amend")
    public String addOrAmendTourist(@Valid @ModelAttribute("tourist") TouristFromModel touristFromModel, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        String action = ((touristFromModel.getId() == null) ? Constant.ACTION_ADD : Constant.ACTION_AMEND);

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
            LocalDate dob = LocalDate.parse(touristFromModel.getDob());

            Tourist tourist = new Tourist();

            // DOB date time validation

            if (dob.isBefore(LocalDate.now())) {
                tourist.setDob(dob);
            } else {
                formIsNotValid = true;
                errors.append("Date of birth - the date cannot be after the present. <br />");
            }

            if (formIsNotValid) {
                model.addAttribute("action", action);
                model.addAttribute("errors", errors.toString());
                return "tourist/add-amend";
            }

            tourist.setFirstName(touristFromModel.getFirstName());
            tourist.setLastName(touristFromModel.getLastName());
            tourist.setGender(touristFromModel.getGender());
            tourist.setCountry(touristFromModel.getCountry());
            tourist.setRemarks(touristFromModel.getRemarks());

            if (action.equals(Constant.ACTION_AMEND)) {
                touristService.updateTourist(tourist, touristFromModel.getId());
            } else {
                touristService.addTourist(tourist);
            }
            redirectAttributes.addFlashAttribute("message", "You successfully " + action + "ed a tourist.");
        } catch (Exception e) {
            log.error("Error message: {}", e.getMessage());
        }

        return "redirect:/tourist";
    }

    @GetMapping("/{id}/flight")
    public String touristFlightsList(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        List<Flight> currentTouristFlights = flightService.getFlightsByTouristsId(id);
        List<Flight> allOtherFlights = flightService.getAllFlights();
        allOtherFlights.removeAll(currentTouristFlights);
        model.addAttribute("currentTouristFlights", currentTouristFlights);
        model.addAttribute("allOtherFlights", allOtherFlights);
        model.addAttribute("touristId", id);
        model.addAttribute("message", message);
        return "/tourist/flight-list";
    }

    @PostMapping("{touristId}/flight/{flightId}/add")
    public String addFlightToTourist(@PathVariable Long flightId, @PathVariable Long touristId, RedirectAttributes redirectAttributes) {
        Flight flight = flightService.addTouristToFlight(touristId, flightId);

        if (flight != null) {
            redirectAttributes.addFlashAttribute("message", "You have successfully added flight with ID " + flightId);
            return "redirect:/tourist/" + touristId + "/flight";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to add flight with ID " + flightId + " to the tourist.");
            return "redirect:/tourist";
        }
    }

    @PostMapping("{touristId}/flight/{flightId}/delete")
    public String removeFlightFromTourist(@PathVariable Long flightId, @PathVariable Long touristId, RedirectAttributes redirectAttributes) {
        Flight flight = flightService.removeTouristFromFlight(touristId, flightId);

        if (flight != null) {
            redirectAttributes.addFlashAttribute("message", "You have successfully removed flight with ID " + flightId);
            return "redirect:/tourist/" + touristId + "/flight";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to remove a flight with ID " + flightId + " from the tourist.");
            return "redirect:/tourist";
        }

    }

    @GetMapping("/{id}/delete")
    public String removeTourist(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isTouristRemoved = touristService.removeTourist(id);
        if (isTouristRemoved) {
            redirectAttributes.addFlashAttribute("message", "You have successfully removed the tourist with ID: " + id);
            return "redirect:/tourist";
        } else {
            redirectAttributes.addFlashAttribute("message", "An error occurred when trying to delete a tourist with ID: " + id);
            return "redirect:/tourist";
        }

    }

}

package eu.treative.spacetourism;

import eu.treative.spacetourism.dto.Gender;
import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.entity.Tourist;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;

@Slf4j
@Component
public class DBInitializer implements CommandLineRunner {

    @Value("${db.initialize.enabled}")
    private boolean dbInit;

    private final TouristService touristService;
    private final FlightService flightService;

    @Autowired
    public DBInitializer(TouristService touristService, FlightService flightService) {
        this.touristService = touristService;
        this.flightService = flightService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (dbInit) {
            log.info("=== Initializing Database with sample data ===");

            // Creating tourists
            Tourist tourist1 = new Tourist("Jane", "Fields", Gender.Female, "United Kingdom", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDate.of(1981, 06, 07));

            Tourist tourist2 = new Tourist("Rosa", "Romero", Gender.Female, "Switzerland", "", LocalDate.of(1975, 02, 25));

            Tourist tourist3 = new Tourist("Wayne", "Mccoy", Gender.Male, "Ireland", "none", LocalDate.of(1989, 11, 14));

            touristService.addTourist(tourist1);
            touristService.addTourist(tourist2);
            touristService.addTourist(tourist3);

            // Creating flights with tourists
            Flight flight1 = new Flight(LocalDateTime.of(2020, Month.FEBRUARY, 20, 06, 30), LocalDateTime.of(2020, Month.FEBRUARY, 20, 10, 45), 2, 199.00);

            flight1.setTourists(new HashSet<Tourist>() {{
                add(tourist1);
                add(tourist2);
            }});

            Flight flight2 = new Flight(LocalDateTime.of(2019, Month.AUGUST, 05, 12, 00), LocalDateTime.of(2019, Month.AUGUST, 05, 18, 20), 10, 255.99);

            flight2.setTourists(new HashSet<Tourist>() {{
                add(tourist3);
            }});

            flightService.addFlight(flight1);
            flightService.addFlight(flight2);

            log.info("=== Database has been initialized ===");

        } else {
            log.info("=== To initialize Database set db.initialize.enabled property to 'true' ===");
        }

    }
}
package eu.treative.spacetourism;

import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.model.Gender;
import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.service.FlightService;
import eu.treative.spacetourism.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

@Slf4j
@SpringBootApplication
public class SpaceTourismApplication implements CommandLineRunner {

    private final TouristService touristService;
    private final FlightService flightService;

    @Autowired
    public SpaceTourismApplication(TouristService touristService, FlightService flightService) {
        this.touristService = touristService;
        this.flightService = flightService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpaceTourismApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("SPACE API ROCKET WITH TOURISTS IS RUNNING");

        Tourist t1 = new Tourist("Adam", "Nowak", Gender.Male, "Poland", "none", new Date());

        touristService.addTourist(t1);

        Flight f1 = new Flight(new Date(), new Date(), 20, new BigDecimal(1999.89));


        Tourist touristToFlight = touristService.getTourist(1L);
        f1.setTourists(new HashSet<Tourist>() {{
            add(touristToFlight);
        }});

        flightService.addFlight(f1);


    }
}

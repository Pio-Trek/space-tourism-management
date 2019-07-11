package eu.treative.spacetourism;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.treative.spacetourism.entity.Flight;
import eu.treative.spacetourism.utils.URLContants;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlightApiTest {

    @LocalServerPort
    private int port;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    // Add compatibility with new JavaTimeModule
    @BeforeAll
    void beforeAllInit() {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayName("Get method")
    class GetTest {

        @Test
        @DisplayName("Get all flights")
        void shouldGetAllFlights() {
            ResponseEntity<String> response = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_FLIGHT, String.class);
            assertAll(
                    () -> assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode())
            );
        }

        @Test
        @DisplayName("Get single flight")
        void shouldGetSingleFlight() throws IOException {
            String flightId = "1";
            ResponseEntity<String> response = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_FLIGHT + "/" + flightId, String.class);

            Flight flight = mapper.readValue(response.getBody(), Flight.class);

            System.out.println(flight.toString());

            assertAll(
                    () -> assertEquals(flightId, flight.getId().toString()),
                    () -> assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode())
            );
        }

    }

}

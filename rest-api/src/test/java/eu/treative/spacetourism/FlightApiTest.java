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
import org.springframework.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightApiTest {

    @LocalServerPort
    private int port;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    void beforeAllInit() {
        // Add compatibility with new JavaTimeModule
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayName("READ")
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

            assertAll(
                    () -> assertEquals(flightId, flight.getId().toString()),
                    () -> assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode())
            );
        }

        @Test
        @DisplayName("Validate get non-existing flight")
        void shouldValidateGetNonExistingFlight() {
            ResponseEntity<String> response = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_FLIGHT + "/999999", String.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

    }

    @Nested
    @DisplayName("CREATE")
    class AddTest {

        @Test
        @DisplayName("Add single flight")
        void shouldAddNewFlight() throws IOException {
            Flight newFlight = new Flight(LocalDateTime.of(2019, Month.DECEMBER, 05, 19, 30), LocalDateTime.of(2019, Month.DECEMBER, 06, 02, 25), 50, 499.00);

            ResponseEntity<String> response = restTemplate.postForEntity(URLContants.PATH + port + URLContants.API_FLIGHT, newFlight, String.class);

            Flight responseFlight = mapper.readValue(response.getBody(), Flight.class);
            newFlight.setId(responseFlight.getId());
            String expected = mapper.writeValueAsString(newFlight);

            assertAll(
                    () -> assertEquals(expected, response.getBody()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode())
            );

        }

        @Test
        @DisplayName("Validate add flight with missing parameter")
        void shouldValidateMissingFlightParameter() {
            Flight wrongFlight = new Flight(null, LocalDateTime.of(2019, Month.DECEMBER, 06, 02, 25), 50, 499.00);

            ResponseEntity<String> response = restTemplate.postForEntity(URLContants.PATH + port + URLContants.API_FLIGHT, wrongFlight, String.class);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        }
    }

    @Nested
    @DisplayName("UPDATE")
    class EditTest {
        @Test
        @DisplayName("Edit existing flight")
        void shouldEditExistingFlight() throws IOException {
            // Get current flight with ID 1
            String flightId = "1";
            ResponseEntity<String> currentResponse = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_FLIGHT + "/" + flightId, String.class);
            Flight expectedUpdateFlight = mapper.readValue(currentResponse.getBody(), Flight.class);

            // Make changes in the flight
            expectedUpdateFlight.setDeparture(LocalDateTime.of(2020, Month.FEBRUARY, 21, 06, 30));
            expectedUpdateFlight.setTicketPrice(219.00);

            // Update flight
            ResponseEntity<String> updatedResponse = restTemplate.exchange(URLContants.PATH + port + URLContants.API_FLIGHT + "/" + flightId, HttpMethod.PUT, new HttpEntity<>(expectedUpdateFlight), String.class);

            Flight currentFlight = mapper.readValue(updatedResponse.getBody(), Flight.class);

            assertAll(
                    () -> assertEquals(expectedUpdateFlight.toString(), currentFlight.toString()),
                    () -> assertEquals(HttpStatus.OK, updatedResponse.getStatusCode())
            );

        }

        @Test
        @DisplayName("Validate update non-existing flight")
        void shouldValidateWrongFlightId() {
            String flightId = "9999";
            Flight flightToUpdate = new Flight(LocalDateTime.of(2020, Month.FEBRUARY, 20, 06, 30), LocalDateTime.of(2020, Month.FEBRUARY, 20, 10, 45), 2, 199.00);
            flightToUpdate.setId(Long.valueOf(flightId));

            // Update flight
            ResponseEntity<String> updatedResponse = restTemplate.exchange(URLContants.PATH + port + URLContants.API_FLIGHT + "/" + flightId, HttpMethod.PUT, new HttpEntity<>(flightToUpdate), String.class);

            assertEquals(HttpStatus.NOT_FOUND, updatedResponse.getStatusCode());
        }

        @Test
        @DisplayName("Validate update flight with wrong parameter")
        void shouldValidateUpdateWrongFlightParameter() {
            String flightId = "1";
            Flight flightToUpdate = new Flight(LocalDateTime.of(2020, Month.FEBRUARY, 20, 06, 30), LocalDateTime.of(2020, Month.FEBRUARY, 20, 10, 45), -5, 0.00);
            flightToUpdate.setId(Long.valueOf(flightId));

            // Update flight
            ResponseEntity<String> updatedResponse = restTemplate.exchange(URLContants.PATH + port + URLContants.API_FLIGHT + "/" + flightId, HttpMethod.PUT, new HttpEntity<>(flightToUpdate), String.class);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, updatedResponse.getStatusCode());

        }
    }

    @Nested
    @DisplayName("REMOVE")
    class RemoveTest {

        @Test
        @DisplayName("Remove existing flight")
        void shouldRemoveFlight() {
            String flightId = "2";
            ResponseEntity<String> response = restTemplate.exchange(URLContants.PATH + port + URLContants.API_FLIGHT + "/" + flightId, HttpMethod.DELETE, null, String.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("Validate remove non-existing flight")
        void shouldValidateRemoveNonExistingFlight() {
            ResponseEntity<String> response = restTemplate.exchange(URLContants.PATH + port + URLContants.API_FLIGHT + "/999999", HttpMethod.DELETE, null, String.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

    }

}

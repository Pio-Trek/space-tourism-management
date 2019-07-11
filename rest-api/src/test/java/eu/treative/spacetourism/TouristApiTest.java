package eu.treative.spacetourism;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.treative.spacetourism.dto.Gender;
import eu.treative.spacetourism.entity.Tourist;
import eu.treative.spacetourism.utils.URLContants;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TouristApiTest {

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
        @DisplayName("Get all tourists")
        void shouldGetAllTourists() {
            ResponseEntity<String> response = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_TOURIST, String.class);

            assertAll(
                    () -> assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode())
            );
        }

        @Test
        @DisplayName("Get single tourist")
        void shouldGetSingleTourist() throws IOException {
            String touristId = "1";
            ResponseEntity<String> response = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_TOURIST + "/" + touristId, String.class);

            Tourist tourist = mapper.readValue(response.getBody(), Tourist.class);

            assertAll(
                    () -> assertEquals(touristId, tourist.getId().toString()),
                    () -> assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode())
            );
        }

        @Test
        @DisplayName("Validate get non-existing tourist")
        void shouldValidateGetNonExistingTourist() {
            ResponseEntity<String> response = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_TOURIST + "/999999", String.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

    }

    @Nested
    @DisplayName("CREATE")
    class AddTest {

        @Test
        @DisplayName("Add single Tourist")
        void shouldAddNewTourist() throws IOException {
            Tourist newTourist = new Tourist("Maxine", "Lourence", Gender.Female, "South Africa", "my note...", LocalDate.of(1965, 04, 18));

            ResponseEntity<String> response = restTemplate.postForEntity(URLContants.PATH + port + URLContants.API_TOURIST, newTourist, String.class);

            Tourist responseTourist = mapper.readValue(response.getBody(), Tourist.class);
            newTourist.setId(responseTourist.getId());
            String expected = mapper.writeValueAsString(newTourist);

            assertAll(
                    () -> assertEquals(expected, response.getBody()),
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode())
            );

        }

        @Test
        @DisplayName("Validate add tourist with missing parameter")
        void shouldValidateMissingTouristParameter() {
            Tourist wrongTourist = new Tourist("", "", Gender.Female, "South Africa", "", LocalDate.of(1965, 04, 18));

            ResponseEntity<String> response = restTemplate.postForEntity(URLContants.PATH + port + URLContants.API_TOURIST, wrongTourist, String.class);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        }
    }

    @Nested
    @DisplayName("UPDATE")
    class EditTest {
        @Test
        @DisplayName("Edit existing tourist")
        void shouldEditExistingTourist() throws IOException {
            // Get current tourist with ID 1
            String touristId = "1";
            ResponseEntity<String> currentResponse = restTemplate.getForEntity(URLContants.PATH + port + URLContants.API_TOURIST + "/" + touristId, String.class);
            Tourist expectedUpdateTourist = mapper.readValue(currentResponse.getBody(), Tourist.class);

            // Make changes in the tourist
            expectedUpdateTourist.setCountry("New Zealand");
            expectedUpdateTourist.setRemarks("Asked for special food");

            // Update tourist
            ResponseEntity<String> updatedResponse = restTemplate.exchange(URLContants.PATH + port + URLContants.API_TOURIST + "/" + touristId, HttpMethod.PUT, new HttpEntity<>(expectedUpdateTourist), String.class);

            Tourist currentTourist = mapper.readValue(updatedResponse.getBody(), Tourist.class);

            assertAll(
                    () -> assertEquals(expectedUpdateTourist.toString(), currentTourist.toString()),
                    () -> assertEquals(HttpStatus.OK, updatedResponse.getStatusCode())
            );

        }

        @Test
        @DisplayName("Validate update non-existing tourist")
        void shouldValidateWrongTouristId() {
            String touristId = "9999";
            Tourist touristToUpdate = new Tourist("Maxine", "Lourence", Gender.Female, "South Africa", "my note...", LocalDate.of(1965, 04, 18));
            touristToUpdate.setId(Long.valueOf(touristId));

            // Update tourist
            ResponseEntity<String> updatedResponse = restTemplate.exchange(URLContants.PATH + port + URLContants.API_TOURIST + "/" + touristId, HttpMethod.PUT, new HttpEntity<>(touristToUpdate), String.class);

            assertEquals(HttpStatus.NOT_FOUND, updatedResponse.getStatusCode());
        }

        @Test
        @DisplayName("Validate update tourist with wrong parameter")
        void shouldValidateUpdateWrongTouristParameter() {
            String touristId = "1";
            Tourist touristToUpdate = new Tourist("Jane", "Fields", Gender.Female, "United Kingdom", "", LocalDate.of(2050, 06, 07)); // Wrong DOB
            touristToUpdate.setId(Long.valueOf(touristId));

            // Update tourist
            ResponseEntity<String> updatedResponse = restTemplate.exchange(URLContants.PATH + port + URLContants.API_TOURIST + "/" + touristId, HttpMethod.PUT, new HttpEntity<>(touristToUpdate), String.class);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, updatedResponse.getStatusCode());

        }
    }

    @Nested
    @DisplayName("REMOVE")
    class RemoveTest {

        @Test
        @DisplayName("Remove existing tourist")
        void shouldRemoveTourist() {
            String touristId = "2";
            ResponseEntity<String> response = restTemplate.exchange(URLContants.PATH + port + URLContants.API_TOURIST + "/" + touristId, HttpMethod.DELETE, null, String.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("Validate remove non-existing tourist")
        void shouldValidateRemoveNonExistingTourist() {
            ResponseEntity<String> response = restTemplate.exchange(URLContants.PATH + port + URLContants.API_TOURIST + "/999999", HttpMethod.DELETE, null, String.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

    }

}

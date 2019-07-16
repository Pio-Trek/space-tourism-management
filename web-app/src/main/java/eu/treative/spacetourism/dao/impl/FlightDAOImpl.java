package eu.treative.spacetourism.dao.impl;

import eu.treative.spacetourism.client.WebClient;
import eu.treative.spacetourism.dao.FlightDAO;
import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.utils.URLContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class FlightDAOImpl implements FlightDAO {

    private final WebClient client;

    @Autowired
    public FlightDAOImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public List<Flight> getAllFlights() {
        try {
            return client.getRestTemplate().exchange(
                    URLContants.URL_FLIGHT,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Flight>>() {
                    }).getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't get list of flights. Error message: {}", e.getResponseBodyAsString());
            return new ArrayList<>();
        }
    }

    @Override
    public Flight getFlight(Long id) {
        Flight responseFlight = null;
        try {
            responseFlight = client.getRestTemplate()
                    .getForEntity(URLContants.URL_FLIGHT + "/" + id, Flight.class).getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't get flight with id: {}. Error message: {}", id, e.getResponseBodyAsString());
        }

        return responseFlight;
    }

    @Override
    public Flight addFlight(Flight flight) {
        Flight responseFlight = new Flight();
        try {
            HttpEntity<Flight> request = new HttpEntity<>(flight);
            responseFlight = client.getRestTemplate()
                    .exchange(URLContants.URL_FLIGHT, HttpMethod.POST, request, Flight.class)
                    .getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't add a new flight. Error message: {}", e.getResponseBodyAsString());
        }
        return responseFlight;
    }

    @Override
    public Flight updateFlight(Flight flight, Long id) {
        Flight responseFlight = new Flight();
        try {
            HttpEntity<Flight> request = new HttpEntity<>(flight);
            responseFlight = client.getRestTemplate()
                    .exchange(URLContants.URL_FLIGHT + "/" + id, HttpMethod.PUT, request, Flight.class)
                    .getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't update a flight. Error message: {}", e.getResponseBodyAsString());
        }
        return responseFlight;
    }

    @Override
    public void removeFight(Long id) {
        try {
            client.getRestTemplate().exchange
                    (URLContants.URL_FLIGHT + "/" + id, HttpMethod.DELETE, null, String.class);
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't delete flight with id: {}. Error message: {}", id, e.getResponseBodyAsString());
        }

    }
}

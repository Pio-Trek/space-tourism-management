package eu.treative.spacetourism.dao.impl;

import eu.treative.spacetourism.client.WebClient;
import eu.treative.spacetourism.dao.FlightDAO;
import eu.treative.spacetourism.model.Flight;
import eu.treative.spacetourism.utils.URLContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlightDAOImpl implements FlightDAO {

    private final WebClient client;

    @Autowired
    public FlightDAOImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public List<Flight> getAllFlights() {
        return client.getRestTemplate().exchange(
                URLContants.URL_FLIGHT,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Flight>>() {
                }).getBody();
    }
}

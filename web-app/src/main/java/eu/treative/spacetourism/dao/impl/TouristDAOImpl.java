package eu.treative.spacetourism.dao.impl;

import eu.treative.spacetourism.client.WebClient;
import eu.treative.spacetourism.dao.TouristDAO;
import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.utils.URLContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class TouristDAOImpl implements TouristDAO {

    private final WebClient client;

    @Autowired
    public TouristDAOImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public List<Tourist> getAllTourists() {
        try {
            return client.getRestTemplate().exchange(
                    URLContants.URL_TOURIST,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Tourist>>() {
                    }).getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't get list of tourists. Error message: {}", e.getResponseBodyAsString());
            return new ArrayList<>();
        }
    }

    @Override
    public Tourist getTourist(Long id) {
        return null;
    }

    @Override
    public Tourist addTourist(Tourist tourist) {
        return null;
    }

    @Override
    public Tourist updateTourist(Tourist tourist, Long id) {
        return null;
    }

    @Override
    public void removeTourist(Long id) {

    }
}

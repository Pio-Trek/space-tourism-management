package eu.treative.spacetourism.dao.impl;

import eu.treative.spacetourism.client.WebClient;
import eu.treative.spacetourism.dao.TouristDAO;
import eu.treative.spacetourism.model.Tourist;
import eu.treative.spacetourism.utils.Constant;
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
                    Constant.URL_TOURIST,
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
        Tourist responseTourist = null;
        try {
            responseTourist = client.getRestTemplate()
                    .getForEntity(Constant.URL_TOURIST + "/" + id, Tourist.class).getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't get tourist with id: {}. Error message: {}", id, e.getResponseBodyAsString());
        }

        return responseTourist;
    }

    @Override
    public Tourist addTourist(Tourist tourist) {
        Tourist responseTourist = new Tourist();
        try {
            HttpEntity<Tourist> request = new HttpEntity<>(tourist);
            responseTourist = client.getRestTemplate()
                    .exchange(Constant.URL_TOURIST, HttpMethod.POST, request, Tourist.class)
                    .getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't add a new tourist. Error message: {}", e.getResponseBodyAsString());
        }
        return responseTourist;
    }

    @Override
    public Tourist updateTourist(Tourist tourist, Long id) {
        Tourist responseTourist = new Tourist();
        try {
            HttpEntity<Tourist> request = new HttpEntity<>(tourist);
            responseTourist = client.getRestTemplate()
                    .exchange(Constant.URL_TOURIST + "/" + id, HttpMethod.PUT, request, Tourist.class)
                    .getBody();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't update a tourist. Error message: {}", e.getResponseBodyAsString());
        }
        return responseTourist;
    }

    @Override
    public boolean removeTourist(Long id) {
        try {
            return client.getRestTemplate().exchange
                    (Constant.URL_TOURIST + "/" + id, HttpMethod.DELETE, null, String.class).getStatusCode().is2xxSuccessful();
        } catch (final HttpClientErrorException e) {
            log.error("Couldn't delete tourist with id: {}. Error message: {}", id, e.getResponseBodyAsString());
        }
        return false;
    }
}

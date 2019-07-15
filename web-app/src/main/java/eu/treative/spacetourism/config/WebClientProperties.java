package eu.treative.spacetourism.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * POJO class of the custom configuration properties to automatically inject
 * properties into the class used to access to the external REST Service.
 */

@Getter
@Setter
@Component
@ConfigurationProperties("rest") // configuration prefix
public class WebClientProperties {

    /**
     * Service connect timeout - how long stay connected
     */
    private int connectTimeout;

    /**
     * Service base url address
     */
    private String serviceUrl;

}

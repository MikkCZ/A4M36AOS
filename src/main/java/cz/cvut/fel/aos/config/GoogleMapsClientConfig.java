package cz.cvut.fel.aos.config;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsClientConfig {

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext().setApiKey("AIzaSyBQiMZewbukKwq5O6bylOfAixQwrtPfqNY");
    }
}

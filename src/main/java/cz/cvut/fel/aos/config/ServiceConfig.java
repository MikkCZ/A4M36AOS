package cz.cvut.fel.aos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import cz.cvut.fel.aos.dao.FlightEntityDao;
import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.entities.ReservationEntity;
import cz.cvut.fel.aos.service.DestinationService;
import cz.cvut.fel.aos.service.FlightService;
import cz.cvut.fel.aos.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import({DataConfig.class, SecurityConfig.class})
public class ServiceConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public DestinationService destinationService(GenericEntityDao<DestinationEntity> destinationDao, GeoApiContext geoApiContext) {
        return new DestinationService(destinationDao, geoApiContext);
    }

    @Bean
    public FlightService flightService(FlightEntityDao flightEntityDao) {
        return new FlightService(flightEntityDao);
    }

    @Bean
    public ReservationService reservationService(GenericEntityDao<ReservationEntity> reservationEntityDao) {
        return new ReservationService(reservationEntityDao, objectMapper);
    }

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext().setApiKey(environment.getRequiredProperty("google.map.api.key"));
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }

}

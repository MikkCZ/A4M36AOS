package cz.cvut.fel.aos.config;

import cz.cvut.fel.aos.dao.FlightEntityDao;
import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.service.DestinationService;
import cz.cvut.fel.aos.service.FlightService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataConfig.class})
public class ServiceConfig {

    @Bean
    public DestinationService destinationService(GenericEntityDao<DestinationEntity> destinationDao) {
        return new DestinationService(destinationDao);
    }

    @Bean
    public FlightService flightService(FlightEntityDao flightEntityDao) {
        return new FlightService(flightEntityDao);
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }

}

package cz.cvut.fel.aos.test;

import cz.cvut.fel.aos.config.ApplicationContextHolder;
import cz.cvut.fel.aos.service.DestinationService;
import cz.cvut.fel.aos.service.FlightService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockServiceConfig {

    @Bean
    public DestinationService destinationServiceMock() {
        return Mockito.mock(DestinationService.class);
    }

    @Bean
    public FlightService flightServiceMock() {
        return Mockito.mock(FlightService.class);
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }
}

package cz.cvut.fel.aos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.aos.config.ServiceConfig;
import cz.cvut.fel.aos.entities.AbstractDatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ContextConfiguration(classes = {ServiceConfig.class, AbstractServiceTest.class})
public abstract class AbstractServiceTest extends AbstractDatabaseTest {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Autowired
    protected ReservationService reservationService;

    @Autowired
    protected DestinationService destinationService;

    @Autowired
    protected FlightService flightService;

}

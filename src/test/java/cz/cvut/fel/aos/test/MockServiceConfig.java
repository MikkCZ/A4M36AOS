package cz.cvut.fel.aos.test;

import cz.cvut.fel.aos.service.DestinationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockServiceConfig {

    @Bean
    public DestinationService destinationServiceMock() {
        return Mockito.mock(DestinationService.class);
    }
}

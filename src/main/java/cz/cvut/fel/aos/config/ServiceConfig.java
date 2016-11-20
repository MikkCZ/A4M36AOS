package cz.cvut.fel.aos.config;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.service.DestinationService;
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

}

package cz.cvut.fel.aos.config;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.entities.ReservationEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataSourceConfig.class})
public class DataConfig {

    @Bean
    public GenericEntityDao<DestinationEntity> destinationEntityDao() {
        return new GenericEntityDao(DestinationEntity.class);
    }

    @Bean
    public GenericEntityDao<FlightEntity> flightEntityDao() {
        return new GenericEntityDao(FlightEntity.class);
    }

    @Bean
    public GenericEntityDao<ReservationEntity> reservationEntityDao() {
        return new GenericEntityDao(ReservationEntity.class);
    }

}

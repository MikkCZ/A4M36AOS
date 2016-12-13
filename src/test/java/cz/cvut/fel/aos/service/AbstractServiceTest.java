package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.config.ServiceConfig;
import cz.cvut.fel.aos.entities.AbstractDatabaseTest;
import cz.cvut.fel.aos.service.DestinationService;
import cz.cvut.fel.aos.service.FlightService;
import cz.cvut.fel.aos.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ServiceConfig.class})
public class AbstractServiceTest extends AbstractDatabaseTest {

    @Autowired
    protected ReservationService reservationService;

    @Autowired
    protected DestinationService destinationService;

    @Autowired
    protected FlightService flightService;

}

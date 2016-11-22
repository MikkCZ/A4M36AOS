package cz.cvut.fel.aos.entities.jackson;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import cz.cvut.fel.aos.config.ApplicationContextHolder;
import cz.cvut.fel.aos.service.FlightService;

public class FlightEntityIdResolver extends SimpleObjectIdResolver {

    private FlightService flightService;

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey id) {
        if (flightService == null) {
            init();
        }
        return flightService.get((Integer)id.key);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return new FlightEntityIdResolver();
    }

    private void init() {
        this.flightService = ApplicationContextHolder.getInstance().getApplicationContext().getBean(FlightService.class);
    }
}

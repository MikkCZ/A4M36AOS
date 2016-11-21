package cz.cvut.fel.aos.entities.jackson;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import cz.cvut.fel.aos.config.ApplicationContextHolder;
import cz.cvut.fel.aos.service.DestinationService;

public class DestinationEntityIdResolver extends SimpleObjectIdResolver {

    private DestinationService destinationService;

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey id) {
        if (destinationService == null) {
            init();
        }
        return destinationService.get((Integer)id.key);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return new DestinationEntityIdResolver();
    }

    private void init() {
        this.destinationService = ApplicationContextHolder.getInstance().getApplicationContext().getBean(DestinationService.class);
    }
}

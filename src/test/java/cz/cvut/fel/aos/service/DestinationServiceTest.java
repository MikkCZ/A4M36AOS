package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.config.ServiceConfig;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = {ServiceConfig.class})
public class DestinationServiceTest extends AbstractDatabaseTest {

    @Autowired
    private DestinationService destinationService;

    @Test
    public void getsAll() {
        createEntities(
                DestinationEntity.builder().name("Prague").build(),
                DestinationEntity.builder().name("Brno").build(),
                DestinationEntity.builder().name("Berlin").build(),
                DestinationEntity.builder().name("London").build()
        );
        List<DestinationEntity> loaded = destinationService.getAll(new QueryParams()).getResults();
        assertThat(loaded, hasSize(4));
    }

    @Test
    public void createsAndGetsById() {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").build();
        destinationService.create(entity);
        DestinationEntity loaded = destinationService.get(entity.getId());
        assertThat(loaded, equalTo(entity));
    }

    @Test
    public void enhancesByGeoApi() {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").build();
        destinationService.create(entity);
        DestinationEntity loaded = destinationService.get(entity.getId());
        assertThat(loaded.getLatitude(), is(not(0f)));
        assertThat(loaded.getLongitude(), is(not(0f)));
    }

    @Test
    public void updates() {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").build();
        DestinationEntity updateTo = DestinationEntity.builder().name("Not Prague").build();
        destinationService.create(entity);
        destinationService.update(entity.getId(), updateTo);
        DestinationEntity loaded = destinationService.get(entity.getId());
        assertThat(loaded.getName(), equalTo(updateTo.getName()));
    }

    @Test
    public void deletes() {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").build();
        destinationService.create(entity);
        assertThat(destinationService.getAll(new QueryParams()).getResults(), hasSize(1));
        destinationService.delete(entity.getId());
        assertThat(destinationService.getAll(new QueryParams()).getResults(), hasSize(0));
    }

}
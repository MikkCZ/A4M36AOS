package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class FlightEntityTest extends AbstractDatabaseTest {

    @Autowired
    private GenericEntityDao<FlightEntity> flightEntityDao;

    @Before
    public void setUp() {
        executeInTransaction(() -> {
            flightEntityDao.getAll().stream().forEach(flightEntityDao::delete);
        });
    }

    @Test
    public void createAndFind() throws Exception {
        Instant departure = Instant.now();
        FlightEntity entity = FlightEntity.builder().name("OK42").departure(departure)
                .from(
                        DestinationEntity.builder().name("Prague").build())
                .to(
                        DestinationEntity.builder().name("Paris").build())
                .build();
        executeInTransaction(() -> flightEntityDao.create(entity));
        executeInTransaction(() -> {
            FlightEntity found = flightEntityDao.findById(entity.getId());
            assertThat(found, is(entity));
            assertThat(found.getName(), is("OK42"));
            assertThat(found.getDeparture(), is(departure));
            assertThat(found.getFrom().getName(), is("Prague"));
            assertThat(found.getTo().getName(), is("Paris"));
        });
    }
}
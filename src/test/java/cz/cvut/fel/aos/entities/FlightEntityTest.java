package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.sorting.TimeRangeFilter;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class FlightEntityTest extends AbstractDatabaseTest {

    @Test
    public void createAndFind() throws Exception {
        ZonedDateTime departure = ZonedDateTime.now();
        FlightEntity entity = FlightEntity.builder().name("OK42").dateOfDeparture(departure)
                .from(
                        DestinationEntity.builder().name("Prague").build())
                .to(
                        DestinationEntity.builder().name("Paris").build())
                .build();
        createEntities(entity);
        executeInTransaction(() -> {
            FlightEntity found = flightEntityDao.findById(entity.getId());
            assertThat(found, is(entity));
            assertThat(found.getName(), is("OK42"));
            assertThat(found.getDateOfDeparture(), is(departure));
            assertThat(found.getFrom().getName(), is("Prague"));
            assertThat(found.getTo().getName(), is("Paris"));
        });
    }

    @Test
    public void getAllFiltered() throws Exception {
        FlightEntity e1 = FlightEntity.builder().name("Prague").dateOfDeparture(ZonedDateTime.now()).build();
        FlightEntity e2 = FlightEntity.builder().name("Brno").dateOfDeparture(ZonedDateTime.now().plus(1, ChronoUnit.DAYS)).build();
        FlightEntity e3 = FlightEntity.builder().name("Berlin").dateOfDeparture(ZonedDateTime.now().minus(1, ChronoUnit.DAYS)).build();
        FlightEntity e4 = FlightEntity.builder().name("London").build();
        createEntities(e1, e2, e3, e4);
        executeInTransaction(() -> {
            TimeRangeFilter filter = new TimeRangeFilter(
                    ZonedDateTime.now().minus(1, ChronoUnit.HOURS),
                    ZonedDateTime.now().plus(2, ChronoUnit.DAYS)
            );
            List<FlightEntity> filtered = flightEntityDao.getFiltered(new QueryParams(), filter);
            assertThat(flightEntityDao.getCountFiltered(filter), is(2L));
            assertThat(filtered, hasSize(2));
            assertThat(filtered, contains(e1, e2));
        });
    }
}
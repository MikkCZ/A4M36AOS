package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReservationEntityTest extends AbstractDatabaseTest {

    @Autowired
    private GenericEntityDao<ReservationEntity> reservationEntityDao;

    @Before
    public void setUp() {
        executeInTransaction(() -> {
            reservationEntityDao.getAll().stream().forEach(reservationEntityDao::delete);
        });
    }

    @Test
    public void createAndFind() throws Exception {
        ReservationEntity entity = ReservationEntity.builder()
                .created(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault()))
                .flight(FlightEntity.builder().dateOfDeparture(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault())).build())
                .build();
        executeInTransaction(() -> reservationEntityDao.create(entity));
        executeInTransaction(() -> {
            ReservationEntity found = reservationEntityDao.findById(entity.getId());
            assertThat(found, is(entity));
            assertThat(found.getCreated(), is(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault())));
            assertThat(found.getFlight().getDateOfDeparture(), is(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault())));
        });
    }
}
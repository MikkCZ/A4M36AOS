package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

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
                .created(Instant.EPOCH)
                .flight(FlightEntity.builder().departure(Instant.ofEpochMilli(100)).build())
                .build();
        executeInTransaction(() -> reservationEntityDao.create(entity));
        executeInTransaction(() -> {
            ReservationEntity found = reservationEntityDao.findById(entity.getId());
            assertThat(found, is(entity));
            assertThat(found.getCreated(), is(Instant.EPOCH));
            assertThat(found.getFlight().getDeparture(), is(Instant.ofEpochMilli(100)));
        });
    }
}
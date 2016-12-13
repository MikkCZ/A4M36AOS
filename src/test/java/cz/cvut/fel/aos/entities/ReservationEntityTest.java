package cz.cvut.fel.aos.entities;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.Is.is;

public class ReservationEntityTest extends AbstractDatabaseTest {

    @Test
    public void createAndFind() throws Exception {
        ReservationEntity entity = ReservationEntity.builder()
                .created(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault()))
                .flight(FlightEntity.builder().dateOfDeparture(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault())).build())
                .build();
        createEntities(entity);
        executeInTransaction(() -> {
            ReservationEntity found = reservationEntityDao.findById(entity.getId());
            assertThat(found, is(entity));
            assertThat(found.getCreated(), is(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault())));
            assertThat(found.getFlight().getDateOfDeparture(), is(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault())));
        });
    }

    @Test
    public void passwordIsSetOnPersist() throws Exception {
        ReservationEntity entity = ReservationEntity.builder()
                .created(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault()))
                .flight(FlightEntity.builder().dateOfDeparture(ZonedDateTime.ofInstant(Instant.ofEpochMilli(100), ZoneId.systemDefault())).build())
                .build();
        assertThat(entity.getPassword(), isEmptyOrNullString());
        createEntities(entity);
        assertThat(entity.getPassword(), not(isEmptyOrNullString()));
        String entityPassword = entity.getPassword();
        executeInTransaction(() -> {
            assertThat(reservationEntityDao.findById(entity.getId()).getPassword(), not(isEmptyOrNullString()));
            assertThat(reservationEntityDao.findById(entity.getId()).getPassword(), equalTo(entityPassword));
        });
    }
}
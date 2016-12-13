package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.entities.ReservationEntity;
import cz.cvut.fel.aos.entities.ReservationState;
import cz.cvut.fel.aos.exceptions.InvalidReservationOperationException;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class ReservationServiceTest extends AbstractDatabaseTest {

    @Test(expected = InvalidReservationOperationException.class)
    public void doesNotCreateReservationOverTheFlightCapacity() {
        // Arrange
        FlightEntity flight = FlightEntity.builder()
                .seats(12)
                .build();
        createEntities(flight);
        ReservationService reservationService = new ReservationService(reservationEntityDao);
        executeInTransaction(() -> {
            reservationService.create(ReservationEntity.builder().flight(flightEntityDao.findById(flight.getId())).seats(10).build());
            reservationService.create(ReservationEntity.builder().flight(flightEntityDao.findById(flight.getId())).seats(1).build());
        });

        // Act
        executeInTransaction(() ->
                reservationService.create(ReservationEntity.builder().flight(flightEntityDao.findById(flight.getId())).seats(10).build())
        );
    }

    @Test(expected = InvalidReservationOperationException.class)
    public void updateFailsFroWrongPassword() {
        // Arrange
        String password = "12345";
        ReservationEntity entity = ReservationEntity.builder().password(password).build();
        createEntities(entity);
        ReservationService reservationService = new ReservationService(reservationEntityDao);

        // Act + Assert
        executeInTransaction(() -> {
            reservationService.updateWithPassword(entity.getId(), ReservationEntity.builder().state(ReservationState.NEW).build(), "wrong"+password);
        });
    }

    @Test
    public void updatesToCancelledStateOnly() {
        // Arrange
        String password = "12345";
        ReservationEntity entity = ReservationEntity.builder().password(password).build();
        createEntities(entity);
        ReservationService reservationService = new ReservationService(reservationEntityDao);

        // Act + Assert
        executeInTransaction(() -> {
            reservationService.updateWithPassword(entity.getId(), ReservationEntity.builder().state(ReservationState.NEW).build(), password);
            assertThat(reservationEntityDao.findById(entity.getId()).getState(), is(not(ReservationState.NEW)));
            reservationService.updateWithPassword(entity.getId(), ReservationEntity.builder().state(ReservationState.CANCELED).build(), password);
            assertThat(reservationEntityDao.findById(entity.getId()).getState(), is(ReservationState.CANCELED));
        });
    }

    @Test
    public void deletesInNewState() {
        // Arrange
        ReservationService reservationService = new ReservationService(reservationEntityDao);
        ReservationEntity entityNew = ReservationEntity.builder().state(ReservationState.NEW).build();
        executeInTransaction(() -> {
            reservationEntityDao.create(entityNew);
            Page<ReservationEntity> page = reservationService.getAll(new QueryParams());
            assertThat(page.getResults(), hasSize(1));
        });

        // Act + Assert
        executeInTransaction(() -> {
            reservationService.delete(entityNew.getId());
            Page<ReservationEntity> page = reservationService.getAll(new QueryParams());
            assertThat(page.getResults(), hasSize(0));
        });
    }

}
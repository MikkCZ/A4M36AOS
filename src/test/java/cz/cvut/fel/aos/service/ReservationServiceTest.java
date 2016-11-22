package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.FlightEntityDao;
import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.entities.ReservationEntity;
import cz.cvut.fel.aos.entities.ReservationState;
import cz.cvut.fel.aos.exceptions.InvalidReservationOperationException;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class ReservationServiceTest extends AbstractDatabaseTest {

    @Autowired
    private FlightEntityDao flightEntityDao;

    @Autowired
    private GenericEntityDao<ReservationEntity> reservationEntityDao;

    @Before
    public void setUp() {
        executeInTransaction(() -> {
            reservationEntityDao.getAll().stream().forEach(reservationEntityDao::delete);
        });
    }

    @Test(expected = InvalidReservationOperationException.class)
    public void doesNotCreateReservationOverTheFlightCapacity() {
        // Arrange
        FlightEntity flight = FlightEntity.builder()
                .seats(12)
                .build();
        executeInTransaction(() -> flightEntityDao.create(flight));
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

    @Test
    public void updatesToCancelledStateOnly() {
        // Arrange
        ReservationEntity entity = ReservationEntity.builder().build();
        executeInTransaction(() -> reservationEntityDao.create(entity));
        ReservationService reservationService = new ReservationService(reservationEntityDao);

        // Act + Assert
        executeInTransaction(() -> {
            reservationService.update(entity.getId(), ReservationEntity.builder().state(ReservationState.NEW).build());
            assertThat(reservationEntityDao.findById(entity.getId()).getState(), is(not(ReservationState.NEW)));
            reservationService.update(entity.getId(), ReservationEntity.builder().state(ReservationState.CANCELED).build());
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
package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.ReservationEntity;
import cz.cvut.fel.aos.exceptions.InvalidReservationOperationException;
import org.springframework.transaction.annotation.Transactional;

import static cz.cvut.fel.aos.entities.ReservationState.CANCELED;
import static cz.cvut.fel.aos.entities.ReservationState.NEW;
import static cz.cvut.fel.aos.entities.ReservationState.PAID;

@Transactional
public class ReservationService extends GenericService<ReservationEntity> {

    public ReservationService(GenericEntityDao<ReservationEntity> entityDao) {
        super(entityDao);
    }

    public void create(ReservationEntity reservationEntity) {
        int reservedSeats = reservationEntity.getFlight().getReservations().stream().mapToInt(r -> r.getSeats()).sum();
        int seatsAvailable = reservationEntity.getFlight().getSeats() - reservedSeats;
        if (seatsAvailable < reservationEntity.getSeats()) {
            throw new InvalidReservationOperationException();
        }
        entityDao.create(reservationEntity);
    }

    public void update(int id, ReservationEntity reservationEntity) {
        ReservationEntity original = get(id);
        if (reservationEntity.getState() == CANCELED) {
            original.setState(CANCELED);
        }
        entityDao.update(original);
    }

    public void pay(int id) {
        ReservationEntity original = get(id);
        original.setState(PAID);
        entityDao.update(original);
    }

    @Override
    public void delete(int id) {
        ReservationEntity entity = get(id);
        if (entity.getState() == NEW) {
            super.delete(id);
        } else {
            throw new InvalidReservationOperationException();
        }
    }
}

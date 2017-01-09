package cz.cvut.fel.aos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.ReservationEntity;
import cz.cvut.fel.aos.entities.ReservationState;
import cz.cvut.fel.aos.exceptions.InvalidReservationOperationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;

import static cz.cvut.fel.aos.entities.ReservationState.*;

@Transactional
public class ReservationService extends GenericService<ReservationEntity> {

    private final ObjectMapper objectMapper;

    public ReservationService(GenericEntityDao<ReservationEntity> entityDao, ObjectMapper objectMapper) {
        super(entityDao);
        this.objectMapper = objectMapper;
    }

    public ReservationEntity getWithPassword(int id, String password) {
        ReservationEntity e = this.get(id);
        checkPassword(e, password);
        return e;
    }

    public void create(ReservationEntity reservationEntity) {
        int reservedSeats = reservationEntity.getFlight().getReservations().stream().mapToInt(ReservationEntity::getSeats).sum();
        int seatsAvailable = reservationEntity.getFlight().getSeats() - reservedSeats;
        if (seatsAvailable < reservationEntity.getSeats()) {
            throw new InvalidReservationOperationException();
        }
        reservationEntity.setState(ReservationState.NEW);
        reservationEntity.setCreated(ZonedDateTime.now());
        entityDao.create(reservationEntity);
    }

    public void updateWithPassword(int id, ReservationEntity reservationEntity, String password) {
        ReservationEntity original = get(id);
        checkPassword(original, password);
        if (reservationEntity.getState() == CANCELLED) {
            original.setState(CANCELLED);
        }
        entityDao.update(original);
    }

    public void pay(int id) {
        ReservationEntity original = get(id);
        original.setState(PAID);
        entityDao.update(original);
    }

    public void mail(int id, String email) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(
                    "http://localhost:8001/print",
                    HttpMethod.POST,
                    new HttpEntity<String>(objectMapper.writeValueAsString(get(id))),
                    String.class
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

    private void checkPassword(ReservationEntity e, String password) {
        if (!e.getPassword().equals(password)) {
            throw new InvalidReservationOperationException();
        }
    }
}

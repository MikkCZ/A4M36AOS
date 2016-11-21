package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.FlightEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FlightService extends GenericService<FlightEntity> {

    public FlightService(GenericEntityDao<FlightEntity> entityDao) {
        super(entityDao);
    }

    public int create(FlightEntity flightEntity) {
        entityDao.create(flightEntity);
        return flightEntity.getId();
    }

    public void update(int id, FlightEntity flightEntity) {
        FlightEntity original = get(id);
        original.setDeparture(flightEntity.getDeparture());
        original.setFrom(flightEntity.getFrom());
        original.setTo(flightEntity.getTo());
        original.setDistance(flightEntity.getDistance());
        original.setPrice(flightEntity.getPrice());
        original.setSeats(flightEntity.getSeats());
        original.setName(flightEntity.getName());
        entityDao.update(flightEntity);
    }
}

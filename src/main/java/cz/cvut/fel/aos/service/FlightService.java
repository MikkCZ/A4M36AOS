package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.FlightEntityDao;
import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.sorting.TimeRangeFilter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class FlightService extends GenericService<FlightEntity> {

    private FlightEntityDao entityDao;

    public FlightService(FlightEntityDao entityDao) {
        super(entityDao);
        this.entityDao = entityDao;
    }

    public Page<FlightEntity> getAll(QueryParams queryParams, Optional<TimeRangeFilter> departureFilter) {
        if (departureFilter.isPresent()) {
            return new Page<>(entityDao.getFiltered(queryParams, departureFilter.get()), entityDao.getCountFiltered(departureFilter.get()));
        } else {
            return new Page<>(entityDao.getAll(queryParams), entityDao.getAllCount());
        }
    }

    public int create(FlightEntity flightEntity) {
        entityDao.create(flightEntity);
        return flightEntity.getId();
    }

    public void update(int id, FlightEntity flightEntity) {
        FlightEntity original = get(id);
        original.setDateOfDeparture(flightEntity.getDateOfDeparture());
        original.setFrom(flightEntity.getFrom());
        original.setTo(flightEntity.getTo());
        original.setDistance(flightEntity.getDistance());
        original.setPrice(flightEntity.getPrice());
        original.setSeats(flightEntity.getSeats());
        original.setName(flightEntity.getName());
        entityDao.update(original);
    }
}

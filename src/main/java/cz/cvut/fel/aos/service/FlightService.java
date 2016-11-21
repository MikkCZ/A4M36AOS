package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.FlightEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FlightService extends GenericService<FlightEntity> {

    public FlightService(GenericEntityDao<FlightEntity> entityDao) {
        super(entityDao);
    }
}

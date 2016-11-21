package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class FlightService {

    private final GenericEntityDao<FlightEntity> flightEntityDao;

    public Page<FlightEntity> getAll(QueryParams queryParams) {
        return new Page(flightEntityDao.getAll(queryParams), flightEntityDao.getAllCount());
    }
}

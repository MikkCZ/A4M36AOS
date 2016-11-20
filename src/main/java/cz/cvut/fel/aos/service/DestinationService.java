package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.resource.params.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public class DestinationService {

    private final GenericEntityDao<DestinationEntity> destinationEntityDao;

    public List<DestinationEntity> getAll(QueryParams queryParams) {
        return destinationEntityDao.getAll(queryParams);
    }

    public DestinationEntity get(int id) {
        return destinationEntityDao.findById(id);
    }

    public int create(DestinationEntity destinationEntity) {
        destinationEntityDao.create(destinationEntity);
        return destinationEntity.getId();
    }

    public void update(int id, DestinationEntity destinationEntity) {
        destinationEntity.setId(id);
        destinationEntityDao.update(destinationEntity);
    }

    public void delete(int id) {
        destinationEntityDao.deleteById(id);
    }
}

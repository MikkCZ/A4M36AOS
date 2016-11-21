package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DestinationService extends GenericService<DestinationEntity> {

    public DestinationService(GenericEntityDao<DestinationEntity> entityDao) {
        super(entityDao);
    }

    public int create(DestinationEntity destinationEntity) {
        entityDao.create(destinationEntity);
        return destinationEntity.getId();
    }

    public void update(int id, DestinationEntity destinationEntity) {
        destinationEntity.setId(id);
        entityDao.update(destinationEntity);
    }
}

package cz.cvut.fel.aos.service;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@RequiredArgsConstructor
@Transactional
public abstract class GenericService<ENTITY extends Serializable> {

    protected final GenericEntityDao<ENTITY> entityDao;

    public Page<ENTITY> getAll(QueryParams queryParams) {
        return new Page(entityDao.getAll(queryParams), entityDao.getAllCount());
    }

    public ENTITY get(int id) {
        return entityDao.findById(id);
    }

    public void delete(int id) {
        entityDao.deleteById(id);
    }
}

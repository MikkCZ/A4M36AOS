package cz.cvut.fel.aos.dao;

import cz.cvut.fel.aos.resource.OrderBy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public class GenericEntityDao<ENTITY extends Serializable> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<ENTITY> cls;

    public GenericEntityDao(Class<ENTITY> cls) {
        this.cls = cls;
    }

    public void create(ENTITY entity) {
        em.persist(entity);
    }

    public ENTITY findById(Serializable id) {
        return em.find(this.cls, id);
    }

    public ENTITY update(ENTITY entity){
        return em.merge(entity);
    }

    public void delete(ENTITY entity){
        em.remove(entity);
    }

    public void deleteById(Serializable id) {
        ENTITY entity = findById(id);
        delete(entity);
    }

    public List<ENTITY> getAll() {
        return getAll(Optional.empty());
    }

    public List<ENTITY> getAll(Optional<OrderBy> orderBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ENTITY> q = cb.createQuery(cls);
        Path<ENTITY> path = (q.from(cls));

        q.select(path);
        if (orderBy.isPresent()) {
            q.orderBy(getOrder(path, cb, orderBy.get()));
        }
        return em.createQuery(q).getResultList();
    }

    private Order getOrder(Path<ENTITY> path, CriteriaBuilder cb, OrderBy orderBy) {
        Path<Object> fieldPath = path.get(orderBy.getField());
        switch (orderBy.getOrder()) {
            case ASC:
                return cb.asc(fieldPath);
            case DESC:
                return cb.desc(fieldPath);
            default:
                throw new IllegalStateException();
        }
    }

}

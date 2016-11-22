package cz.cvut.fel.aos.dao;

import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.pagination.Pagination;
import cz.cvut.fel.aos.resource.params.sorting.OrderBy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import java.io.Serializable;
import java.util.List;

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
        return getAll(new QueryParams());
    }

    public List<ENTITY> getAll(QueryParams queryParams) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ENTITY> q = cb.createQuery(cls);
        Path<ENTITY> path = q.from(cls);

        q.select(path);
        if (queryParams.getOrderBy().isPresent()) {
            q.orderBy(getOrder(path, cb, queryParams.getOrderBy().get()));
        }
        TypedQuery<ENTITY> typedQuery = em.createQuery(q);
        if (queryParams.getPagination().isPresent()) {
            typedQuery = pagination(typedQuery, queryParams.getPagination().get());
        }
        return typedQuery.getResultList();
    }

    public long getAllCount() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Path<ENTITY> path = q.from(cls);

        q.select(cb.count(path));
        return em.createQuery(q).getSingleResult();
    }

    protected Order getOrder(Path<ENTITY> path, CriteriaBuilder cb, OrderBy orderBy) {
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

    protected TypedQuery<ENTITY> pagination(TypedQuery<ENTITY> typedQuery, Pagination pagination) {
        return typedQuery.setFirstResult(pagination.getOffset()).setMaxResults(pagination.getBase());
    }

}

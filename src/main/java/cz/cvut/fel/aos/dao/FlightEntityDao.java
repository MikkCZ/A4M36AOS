package cz.cvut.fel.aos.dao;

import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.sorting.TimeRangeFilter;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import java.util.List;

public class FlightEntityDao extends GenericEntityDao<FlightEntity> {

    public FlightEntityDao() {
        super(FlightEntity.class);
    }

    public List<FlightEntity> getFiltered(QueryParams queryParams, TimeRangeFilter timeRangeFilter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FlightEntity> q = cb.createQuery(FlightEntity.class);
        Path<FlightEntity> path = q.from(FlightEntity.class);

        q.select(path);
        if (queryParams.getOrderBy().isPresent()) {
            q.orderBy(getOrder(path, cb, queryParams.getOrderBy().get()));
        }
        constructWhere(q, cb, path, timeRangeFilter);
        TypedQuery<FlightEntity> typedQuery = em.createQuery(q);
        if (queryParams.getPagination().isPresent()) {
            typedQuery = pagination(typedQuery, queryParams.getPagination().get());
        }
        return typedQuery.getResultList();
    }

    public long getCountFiltered(TimeRangeFilter timeRangeFilter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Path<FlightEntity> path = q.from(FlightEntity.class);

        q.select(cb.count(path));
        constructWhere(q, cb, path, timeRangeFilter);
        return em.createQuery(q).getSingleResult();
    }

    public void constructWhere(CriteriaQuery q, CriteriaBuilder cb, Path<FlightEntity> path, TimeRangeFilter timeRangeFilter) {
        q.where(cb.between(path.get("dateOfDeparture"), timeRangeFilter.getFrom(), timeRangeFilter.getTo()));
    }
}

package cz.cvut.fel.aos.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
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
        CriteriaQuery<ENTITY> cq = em.getCriteriaBuilder().createQuery(cls);
        return em.createQuery(cq.select(cq.from(cls))).getResultList();
    }

}

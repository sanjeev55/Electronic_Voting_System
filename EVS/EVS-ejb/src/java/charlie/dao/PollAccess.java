package charlie.dao;

import charlie.dao.filter.PollSearchFilter;
import charlie.dao.filter.SearchOrderEnum;
import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
import charlie.domain.Page;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class PollAccess extends AbstractAccess<PollEntity> {

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;
    
    @EJB
    private PollOwnerAccess pollOwnerDao;

    public PollAccess() {
        super(PollEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Page<PollEntity> findAll(PollSearchFilter filter) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PollEntity> criteriaQuery = cb.createQuery(PollEntity.class);
        Root<PollEntity> pollRoot = criteriaQuery.from(PollEntity.class);
        Join<PollEntity, PollOwnerEntity> pollOwners = pollRoot.join("pollOwners");

        Predicate predicateForPollState = cb.equal(pollRoot.get("state"), filter.getState());
        Predicate predicateForOrganizer = cb.equal(pollOwners.get("organizer"), filter.getOrganizer());
        Predicate predicateForOwnPoll = cb.equal(pollOwners.get("isPrimaryOrganizer"), filter.getFilterPrimaryOrganizer());

        List<Predicate> list = new ArrayList<>();
        list.add(predicateForOwnPoll);
        if (filter.getOrganizer() != null) {
            list.add(predicateForOrganizer);
        }

        if (filter.getState() != null) {
            list.add(predicateForPollState);
        }

        List<Order> orders = new ArrayList<>();
        if (filter.getSortOrder().equals(SearchOrderEnum.ASC)) {
            orders.add(cb.asc(pollRoot.get(filter.getSortField())));
        } else {
            orders.add(cb.desc(pollRoot.get(filter.getSortField())));
        }

        criteriaQuery.where(list.toArray(new Predicate[list.size()])).orderBy(orders);
        List<PollEntity> pollEntities = em.createQuery(criteriaQuery).setFirstResult(filter.getPageSize() * (filter.getPageNumber() - 1))
                .setMaxResults(filter.getPageSize())
                .getResultList();

        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        cr.select(cb.count(pollRoot)).where(list.toArray(new Predicate[list.size()]));
        Long count = em.createQuery(cr).getSingleResult();

        return new Page<>(
                count,
                filter.getPageSize(),
                filter.getPageNumber(),
                pollEntities
        );

    }

    public void deleteById(int id) {
        pollOwnerDao.deleteByPollId(id);
        em.createNamedQuery("deleteById")
                .setParameter("id", id)
                .executeUpdate();
    }
}
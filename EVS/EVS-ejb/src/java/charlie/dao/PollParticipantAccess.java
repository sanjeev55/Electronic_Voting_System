package charlie.dao;

import charlie.entity.PollEntity;
import charlie.entity.PollParticipantEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PollParticipantAccess extends AbstractAccess<PollParticipantEntity> {

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PollParticipantAccess() {
        super(PollParticipantEntity.class);
    }

    public Long countPollParticipantByPoll(PollEntity poll) {
        return em.createNamedQuery("countPollParticipantsByPollId", Long.class)
                .setParameter("poll", poll)
                .getSingleResult();
    }

    public List<PollParticipantEntity> findAllByPoll(PollEntity poll) {
        return em.createNamedQuery("getPollParticipantsByPoll")
                .setParameter("poll", poll)
                .getResultList();
    }

    // 1. Find by Token

    public PollParticipantEntity findByToken(String token) {
        try {
            return em.createNamedQuery("findByToken", PollParticipantEntity.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<PollParticipantEntity> findParticipantsByParticipationStatus(Boolean hasParticipated) {
        return em.createNamedQuery("findParticipantsByParticipationStatus", PollParticipantEntity.class)
                .setParameter("hasParticipated", hasParticipated)
                .getResultList();
    }

    public PollParticipantEntity findByEmail(String email){
        return em.createNamedQuery("getPollParticipantByEmail", PollParticipantEntity.class)
                .setParameter("pollId", email)
                .getSingleResult();
    }
  
    public void deleteByPollId(PollEntity poll){
        em.createNamedQuery("deleteAllByPollId", PollParticipantEntity.class)
                .setParameter("poll", poll)
                .executeUpdate();
    }
    
    public Long countPollPaticipantByPollIdAndStatus(PollEntity poll, Boolean hasParticipated){
        return em.createNamedQuery("countPollParticipantByPollIdAndStatus", Long.class)
                .setParameter("poll", poll)
                .setParameter("hasParticipated", hasParticipated)
                .getSingleResult();
    }
    
    
}

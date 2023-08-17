package charlie.dao;

import charlie.entity.PollEntity;
import charlie.entity.PollParticipantEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    
}

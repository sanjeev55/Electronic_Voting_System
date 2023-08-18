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
        // 1. Find by Token
    public PollParticipantEntity findByToken(String token) {
        return em.createNamedQuery("findByToken", PollParticipantEntity.class)
                .setParameter("token", token)
                .getSingleResult();
    }

    // 2. Find Poll Participants by a Specific Poll
    public List<PollParticipantEntity> findParticipantsByPoll(PollEntity poll) {
        return em.createNamedQuery("findParticipantsByPoll", PollParticipantEntity.class)
                .setParameter("poll", poll)
                .getResultList();
    }

    // 3. Find Participants who have/haven't participated
    public List<PollParticipantEntity> findParticipantsByParticipationStatus(Boolean hasParticipated) {
        return em.createNamedQuery("findParticipantsByParticipationStatus", PollParticipantEntity.class)
                .setParameter("hasParticipated", hasParticipated)
                .getResultList();
    }
    
    public PollParticipantEntity findByPollIdAndParticipantId(int pollId, int participantId){
        return em.createNamedQuery("findByPollIdAndParticipantId", PollParticipantEntity.class)
                .setParameter("pollId", pollId)
                .setParameter("participantId", participantId)
                .getSingleResult();
    }
    
    
}

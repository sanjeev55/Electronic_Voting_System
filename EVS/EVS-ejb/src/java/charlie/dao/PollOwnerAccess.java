package charlie.dao;

import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
import charlie.entity.UserEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PollOwnerAccess extends AbstractAccess<PollOwnerEntity> {

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PollOwnerAccess() {
        super(PollOwnerEntity.class);
    }

    public void deleteByPollId(int pollId) {
        em.createNamedQuery("deleteByPollId")
                .setParameter(1, pollId)
                .executeUpdate();
    }
    
    public List<PollOwnerEntity> findAllByPoll(PollEntity poll) {
       return em.createNamedQuery("getByPoll", PollOwnerEntity.class)
                .setParameter("poll", poll)
                .getResultList();
                
    }
    
    public List<PollOwnerEntity> findAllByPollId(int pollId) {
       return em.createNamedQuery("getByPollId", PollOwnerEntity.class)
                .setParameter("pollId", pollId)
                .getResultList();
                
    }
    
    public void deleteById(int id) {
        em.createNamedQuery("deletePollOwnerById")
                .setParameter("id", id)
                .executeUpdate();
    }
    
    public void deleteByOrganizer(UserEntity user){
        em.createNamedQuery("deleteByOrganizer", PollOwnerEntity.class)
                .setParameter("organizer", user)
                .executeUpdate();
    }
    
    public List<PollOwnerEntity> getAllByOrganizerAndIsPrimaryOrganizer(UserEntity organizer, Boolean isPrimaryOrganizer){
        return em.createNamedQuery("findAllByOrganizerAndIsPrimaryOrganizer", PollOwnerEntity.class)
                .setParameter("organizer", organizer)
                .setParameter("isPrimaryOrganizer", isPrimaryOrganizer)
                .getResultList();
    }
    
    
}

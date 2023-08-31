package charlie.dao;

import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
import charlie.entity.PollStateEnum;
import charlie.entity.UserEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotAuthorizedException;

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
    
    public void deleteAllByPoll(PollEntity poll){
        em.createNamedQuery("deleteAllByPoll", PollOwnerEntity.class)
                .setParameter("poll", poll)
                .executeUpdate();
    }
    
    public List<PollOwnerEntity> getAllByOrganizerAndPollState(UserEntity organizer, PollStateEnum state){
        return em.createNamedQuery("findAllByOrganizerAndPollState", PollOwnerEntity.class)
                .setParameter("organizer", organizer)
                .setParameter("state", state)
                .getResultList();
    }
    
    public PollOwnerEntity getByPollAndIsPrimaryOraginzer(PollEntity poll, Boolean isPrimaryOrganizer){
        try{
            return em.createNamedQuery("findByPollAndIsPrimaryOrganizer", PollOwnerEntity.class)
                    .setParameter("poll", poll)
                    .setParameter("isPrimaryOrganizer", isPrimaryOrganizer)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
                
    } 
    
    public List<PollOwnerEntity> getAllByOrganizer(UserEntity organizer){
        return em.createNamedQuery("findAllByOrganizer", PollOwnerEntity.class)
                .setParameter("organizer", organizer)
                .getResultList();
    }
    
    public PollOwnerEntity getPollByOrganizer(UserEntity organizer, PollEntity poll){
        try{
        return em.createNamedQuery("findPollByOrganizer", PollOwnerEntity.class)
                .setParameter("organizer", organizer)
                .setParameter("poll", poll)
                .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
}

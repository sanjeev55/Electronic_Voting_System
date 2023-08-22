package charlie.dao;

import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
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
    
    public void deleteById(int id) {
        em.createNamedQuery("deletePollOwnerById")
                .setParameter("id", id)
                .executeUpdate();
    }
}

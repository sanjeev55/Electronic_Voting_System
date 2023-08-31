package charlie.dao;

import charlie.entity.PollQuestionEntity;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PollQuestionAnswerAccess extends AbstractAccess<PollQuestionEntity>{

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;
    
    @EJB
    private PollAccess pollDao;

    public PollQuestionAnswerAccess() {
        super(PollQuestionEntity.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<PollQuestionEntity> getPollQuestionsByPollId(Integer id) {
        var poll = pollDao.find(id);
        if(poll == null)
            return Collections.emptyList();
        
        return em.createNamedQuery("getPollQuestionByPoll")
                .setParameter("poll", poll)
                .getResultList();
    }
    
    public void deleteById(Integer id) {
        em.createNamedQuery("deletePollQuestionById")
                .setParameter(1, id)
                .executeUpdate();
    }
}

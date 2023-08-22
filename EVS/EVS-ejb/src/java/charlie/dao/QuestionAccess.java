package charlie.dao;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.entity.PollQuestionEntity;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;



@Stateless
@LocalBean
public class QuestionAccess extends AbstractAccess<PollQuestionEntity> {
    
    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;
   
    public QuestionAccess() {
        super(PollQuestionEntity.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    //QUESTION: add poll owner access?
    public void deleteById(int questionId) {
        em.createNamedQuery("deleteByQuestionId")
                .setParameter(1, questionId)//parameter 1 is added from pollOwnerAccess template
                .executeUpdate();
    }
    
    public PollQuestionEntity getQuestionById(int id) {
        try {
            return em.createNamedQuery("getQuestionById", PollQuestionEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public PollQuestionEntity getQuestionForEdit (String uuid) {
        return em.createNamedQuery("getPollByUuid", PollQuestionEntity.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }
    
    public void updateQuestion (PollQuestionEntity qe) {
        edit(qe);
    }
}










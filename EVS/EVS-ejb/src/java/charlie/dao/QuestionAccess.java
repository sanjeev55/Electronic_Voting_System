package charlie.dao;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.entity.PollQuestionEntity;
import charlie.entity.QuestionTypeEnum;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


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
    
    /*public void deleteById(int questionId) {
        em.createNamedQuery("deleteByQuestionId")
                .setParameter(1, questionId)//parameter 1 is added from pollOwnerAccess template
                .executeUpdate();
    }*/
    
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
        try{
            return em.createNamedQuery("getPollByUuid", PollQuestionEntity.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public void updateQuestion (PollQuestionEntity qe) {
        edit(qe);
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.dao;

import charlie.entity.QuestionAnswerChoiceEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class QuestionAnswerChoiceAccess extends AbstractAccess<QuestionAnswerChoiceEntity> {

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;

    public QuestionAnswerChoiceAccess() {
        super(QuestionAnswerChoiceEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<QuestionAnswerChoiceEntity> findAllByQuestionId(int questionId) {
        List<QuestionAnswerChoiceEntity> qace = em.createNamedQuery("getAllByQuestionId", QuestionAnswerChoiceEntity.class)
                .setParameter("questionId", questionId)
                .getResultList();

        return qace;
    }

    public void deleteByQuestionId(Integer questionId) {
        em.createNamedQuery("deleteByQuestionId")
                .setParameter(1, questionId)
                .executeUpdate();
    }
}

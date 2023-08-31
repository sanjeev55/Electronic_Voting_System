package charlie.dao;

import charlie.domain.ParticipantResponse;
import charlie.entity.ParticipantQuestionAnswerEntity;
import charlie.entity.PollEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ParticipantQuestionAnswerAccess extends AbstractAccess<ParticipantQuestionAnswerEntity> {

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;

    public ParticipantQuestionAnswerAccess() {
        super(ParticipantQuestionAnswerEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void saveParticipantResponse(ParticipantResponse response) {
        System.out.println(this.em.isOpen());
        em.createNamedQuery("saveParticipantResponse")
                .setParameter(1, response.getQuestionId())
                .setParameter(2, response.getAnswerId())
                .setParameter(3, response.getPollId())
                .setParameter(4, response.getUuid())
                .setParameter(5, response.getJpaVersion())
                .executeUpdate();
    }

    public void saveParticipantResponses(List<ParticipantResponse> responses) {
        responses.forEach(resp -> this.saveParticipantResponse(resp));
    }

    public List<ParticipantQuestionAnswerEntity> findAllByPoll(PollEntity poll) {
        List<ParticipantQuestionAnswerEntity> pqae = em.createNamedQuery("getAllByPoll", ParticipantQuestionAnswerEntity.class)
                .setParameter("poll", poll)
                .getResultList();

        return pqae;
    }

    public List<Integer> getParticipantQuestionAnswerIdsByPollId(Integer pollId) {
        return em.createNamedQuery("getParticipantQuestionAnswerIdsByPollId", Integer.class)
                .setParameter(1, pollId)
                .getResultList();
    }
    
    public void deleteById(Integer id) {
        em.createNamedQuery("deleteByParticipantQuestionAnswerById")
                .setParameter(1, id)
                .executeUpdate();
    }
}

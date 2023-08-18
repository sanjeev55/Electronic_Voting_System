/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.dao;

import charlie.entity.ParticipantEntity;
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
public class ParticipantAccess extends AbstractAccess<ParticipantEntity> {
    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipantAccess() {
        super(ParticipantEntity.class);
    }

    public List<ParticipantEntity> findParticipantsByOrganizer(int organizerId) {
        return em.createNamedQuery("findByOrganizerId", ParticipantEntity.class)
                .setParameter("organizerId", organizerId)
                .getResultList();
    }

    public List<ParticipantEntity> findParticipantsByPoll(int pollId) {
        return em.createNamedQuery("findByPollId", ParticipantEntity.class)
                .setParameter("pollId", pollId)
                .getResultList();
    }
    
    public ParticipantEntity findParticipantByEmail(String email){
        return em.createNamedQuery("findParticipantByEmail", ParticipantEntity.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}

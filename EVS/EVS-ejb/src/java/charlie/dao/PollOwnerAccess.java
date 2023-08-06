package charlie.dao;

import charlie.entity.PollOwnerEntity;
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
}

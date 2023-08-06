package charlie.dao;

import charlie.entity.PollEntity;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@LocalBean
public class PollAccess extends AbstractAccess<PollEntity>{

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;   

    public PollAccess() {
        super(PollEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

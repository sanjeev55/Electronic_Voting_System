package charlie.dao;

import charlie.entity.UserEntity;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserAccess extends AbstractAccess<UserEntity> {

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;   

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAccess() {
        super(UserEntity.class);
    }

    public UserEntity getUser(String userName) {
        UserEntity ue;
        try {
            ue = em.createNamedQuery("getUserByUserName", UserEntity.class)
                    .setParameter("username", userName)
                    .getSingleResult();
        } catch (NoResultException e) {
            ue = new UserEntity(true);
            ue.setUsername(userName);
            ue.setFirstName(userName);
            ue.setLastName(userName);
            em.persist(ue);
        }
        return ue;
    }
    
    public UserEntity getByUuid(String uuid) {
        try {
            return em.createNamedQuery("getUserByUuid", UserEntity.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Entity with specified UUID doesn't exist
            return null;
        }
    }

    
}

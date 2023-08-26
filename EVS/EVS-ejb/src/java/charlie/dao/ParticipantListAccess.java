package charlie.dao;

import charlie.entity.ParticipantListEntity;
import charlie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ParticipantListAccess extends AbstractAccess<ParticipantListEntity> {

    @PersistenceContext(unitName = "EVS-ejbPU")
    private EntityManager em;

    public ParticipantListAccess() {
        super(ParticipantListEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public ParticipantListEntity create(ParticipantListEntity entity) {
        if (!StringUtils.hasText(entity.getEmail())) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        Type listType = new TypeToken<Set<String>>() {
        }.getType();
        Gson gson = new Gson();
        try {
            gson.fromJson(entity.getEmail(), listType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid json: " + e.getMessage());
        }

        return super.create(entity);
    }
    
    public List<ParticipantListEntity> findParticipantListByOrganizerId(int organizerId){
        List<ParticipantListEntity> ple = em.createNamedQuery("findParticipantListByOrganizer", ParticipantListEntity.class)
                .setParameter("organizerId", organizerId)
                .getResultList();
        
        return ple;
    }
    
    public void deleteParicipantListByPollId(int organizerId){
        em.createNamedQuery("deleteParicipantListByOrganizerId", ParticipantListEntity.class)
                .setParameter("organizerId", organizerId)
                .executeUpdate();
    }

}

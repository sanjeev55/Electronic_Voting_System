package charlie.mapper;

import charlie.dto.ParticipantListDto;
import charlie.dto.UserDto;
import charlie.entity.ParticipantListEntity;
import charlie.logic.UserLogic;
import charlie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ParticipantListEntityMapper extends AbstractEntityMapper<ParticipantListEntity, ParticipantListDto> {
    @EJB
    private UserLogic ul;
    @EJB
    private UserEntityMapper uem;
    
    @Override
    public ParticipantListDto toDto(ParticipantListEntity entity) {
        if (entity == null) {
            return null;
        }

        super.setDto(new ParticipantListDto());
        ParticipantListDto dto = super.toDto(entity);
        Gson gson = new Gson();
        Type listType = new TypeToken<Set<String>>(){}.getType();
        dto.setName(entity.getName());
        dto.setEmails(StringUtils.hasText(entity.getEmail()) ? gson.fromJson(entity.getEmail(), listType) : Collections.emptySet());
        dto.setOrganizerId(entity.getOrganizer().getId());
        return dto;
    }

    @Override
    public ParticipantListEntity toEntity(ParticipantListDto domain) {
        if (domain == null) {
            return null;
        }
        
        UserDto userDto = ul.getUserById(domain.getOrganizerId());
        super.setEntity(new ParticipantListEntity());
        ParticipantListEntity entity = super.toEntity(domain);
        Gson gson = new Gson();
        entity.setEmail((domain.getEmails() != null && domain.getEmails().size() > 0) ? gson.toJson(domain.getEmails()) : null);
        entity.setName(domain.getName());
        entity.setOrganizer(uem.toEntity(userDto));
        return entity;
    }
}

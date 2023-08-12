package charlie.mapper;

import charlie.dto.ParticipantListDto;
import charlie.entity.ParticipantListEntity;
import charlie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ParticipantListEntityMapper extends AbstractEntityMapper<ParticipantListEntity, ParticipantListDto> {

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
        return dto;
    }

    @Override
    public ParticipantListEntity toEntity(ParticipantListDto domain) {
        if (domain == null) {
            return null;
        }
        
        super.setEntity(new ParticipantListEntity());
        ParticipantListEntity entity = super.toEntity(domain);
        Gson gson = new Gson();
        entity.setEmail((domain.getEmails() != null && domain.getEmails().size() > 0) ? gson.toJson(domain.getEmails()) : null);
        entity.setName(domain.getName());
        return entity;
    }
}

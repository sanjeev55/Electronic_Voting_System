package charlie.mapper;

import charlie.dto.PollOwnerDto;
import charlie.entity.PollOwnerEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PollOwnerEntityMapper extends AbstractEntityMapper<PollOwnerEntity, PollOwnerDto> {

    @Override
    public PollOwnerDto toDto(PollOwnerEntity entity) {
        if (entity == null) {
            return null;
        }

        super.setDto(new PollOwnerDto());
        PollOwnerDto dto = super.toDto(entity);
        dto.setUsername(entity.getOrganizer().getUsername());
        dto.setPrimaryOrganizer(entity.getPrimaryOrganizer());
        dto.setPollId(entity.getPoll().getId());
        dto.setOrganizerId(entity.getOrganizer().getId());
        return dto;
    }

    public List<PollOwnerDto> toDomainList(List<PollOwnerEntity> entities) {
        if (entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}

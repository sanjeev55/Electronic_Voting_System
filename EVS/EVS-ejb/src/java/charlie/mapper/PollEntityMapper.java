package charlie.mapper;

import charlie.dto.PollDto;
import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
import charlie.utils.DateUtils;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PollEntityMapper extends AbstractEntityMapper<PollEntity, PollDto> {

    @EJB
    private UserEntityMapper userEntityMapper;
    
    @Override
    public PollEntity toEntity(PollDto domain) {

        if (domain == null) {
            return null;
        }

        super.setEntity(new PollEntity());
        PollEntity entity = super.toEntity(domain);
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setTrackParticipant(domain.getTrackParticipant());
        entity.setState(domain.getState());
        entity.setStartsAt(DateUtils.parseDate(domain.getStartsAt()));
        entity.setEndsAt(DateUtils.parseDate(domain.getEndsAt()));

        return entity;

    }

    @Override
    public PollDto toDto(PollEntity entity) {

        if (entity == null) {
            return null;
        }
        
        super.setDto(new PollDto());
        PollDto dto = super.toDto(entity);
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setTrackParticipant(entity.getTrackParticipant());
        dto.setStartsAt(entity.getStartsAt() != null ? entity.getStartsAt().toString() : null);
        dto.setEndsAt(entity.getEndsAt() != null ? entity.getEndsAt().toString() : null);
        dto.setState(entity.getState());
        Optional<PollOwnerEntity> pollOwnerEntity = entity.getPollOwners().stream().filter(pollOwner -> pollOwner.getPrimaryOrganizer() == Boolean.TRUE)
                .findFirst();
        
        if(pollOwnerEntity.isPresent()) {
            dto.setPrimaryOrganizer(userEntityMapper.toDto(pollOwnerEntity.get().getOrganizer()));
        }
        return dto;
    }
    
    public List<PollDto> toDomainList(List<PollEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();

        return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }

}

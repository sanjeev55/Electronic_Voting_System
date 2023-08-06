package charlie.mapper;

import charlie.dto.PollDto;
import charlie.entity.PollEntity;
import charlie.utils.DateUtils;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PollEntityMapper extends AbstractEntityMapper<PollEntity, PollDto> {

    public PollEntityMapper() {
        super(new PollEntity(), new PollDto());
    }

    @Override
    public PollEntity toEntity(PollDto domain) {

        if (domain == null) {
            return null;
        }

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

        PollDto dto = super.toDto(entity);
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setTrackParticipant(entity.getTrackParticipant());
        dto.setStartsAt(entity.getStartsAt() != null ? entity.getStartsAt().toString() : null);
        dto.setEndsAt(entity.getEndsAt() != null ? entity.getEndsAt().toString() : null);
        dto.setState(entity.getState());
        return dto;
    }
}

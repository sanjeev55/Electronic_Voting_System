package charlie.mapper;

import charlie.dto.PollParticipantDto;
import charlie.entity.PollParticipantEntity;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PollParticipantMapper extends AbstractEntityMapper<PollParticipantEntity, PollParticipantDto> {
    
    @EJB
    private PollEntityMapper pollEntityMapper; 

    @Override
    public PollParticipantEntity toEntity(PollParticipantDto dto) {
        if (dto == null) {
            return null;
        }

        super.setEntity(new PollParticipantEntity());
        PollParticipantEntity entity = super.toEntity(dto);
        entity.setToken(dto.getToken());
        entity.setHasParticipated(dto.getHasParticipated());
        entity.setEmail(dto.getEmail());
        entity.setPoll(pollEntityMapper.toEntity(dto.getPoll()));


        return entity;
    }

    @Override
    public PollParticipantDto toDto(PollParticipantEntity entity) {
        if (entity == null) {
            return null;
        }

        super.setDto(new PollParticipantDto());
        PollParticipantDto dto = super.toDto(entity);
        dto.setToken(entity.getToken());
        dto.setHasParticipated(entity.getHasParticipated());
        dto.setEmail(entity.getEmail());
        dto.setPoll(entity.getPoll() != null ? pollEntityMapper.toDto(entity.getPoll()) : null);
        return dto;
    }
}

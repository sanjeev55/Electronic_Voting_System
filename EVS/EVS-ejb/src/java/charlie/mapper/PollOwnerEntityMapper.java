package charlie.mapper;

import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.UserDto;
import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
import charlie.logic.PollLogic;
import charlie.logic.UserLogic;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PollOwnerEntityMapper extends AbstractEntityMapper<PollOwnerEntity, PollOwnerDto> {
    @EJB
    private PollEntityMapper pm;
    
    @EJB
    private PollLogic pl;
    
    @EJB
    private UserLogic ul;
    
    @EJB
    private UserEntityMapper um;
    
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
    
    public PollOwnerEntity toEntity(PollOwnerDto dto) {
    if (dto == null) {
        return null;
    }

    PollOwnerEntity entity = new PollOwnerEntity();
    
    PollDto pollDto = pl.getPollById(dto.getId());
    UserDto userDto = ul.getUserById(dto.getOrganizerId());
    
    entity.setPoll(pm.toEntity(pollDto)); // 
    entity.setPrimaryOrganizer(dto.getPrimaryOrganizer());
    entity.setOrganizer(um.toEntity(userDto));


    return entity;
}
}

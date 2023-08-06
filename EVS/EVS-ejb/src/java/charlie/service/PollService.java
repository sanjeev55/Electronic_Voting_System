package charlie.service;

import charlie.dao.PollAccess;
import charlie.dto.PollDto;
import charlie.entity.PollEntity;
import charlie.entity.PollStateEnum;
import charlie.mapper.PollEntityMapper;
import charlie.response.Result;
import charlie.utils.StringUtils;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PollService {
    
    @EJB
    private PollEntityMapper pollEntityMapper;
    
    @EJB
    private PollAccess pollDao;
    
    public Result<PollDto> save(PollDto domain) {
        if (domain == null)
            return Result.error("Cannot accept null values");

        domain.setState(PollStateEnum.PREPARED);
        domain.setUuid(UUID.randomUUID().toString());

        PollEntity entity = pollEntityMapper.toEntity(domain);
        try {
            PollEntity savedEntity = pollDao.create(entity);

            return Result.ok(pollEntityMapper.toDto(savedEntity));
        } catch (Exception e) {
            if(StringUtils.hasText(e.getMessage()) && e.getMessage().contains("SQLIntegrityConstraintViolationException")) {
                return Result.error(String.format("%s already exists", domain.getTitle()));
            }
            return Result.error(e.getMessage());
        }
    }

    
}

package charlie.service;

import charlie.dao.PollAccess;
import charlie.dao.PollOwnerAccess;
import charlie.dao.PollParticipantAccess;
import charlie.dao.filter.PollSearchFilter;
import charlie.dao.filter.SearchOrderEnum;
import charlie.domain.Page;
import charlie.domain.PollPaginationRequest;
import charlie.dto.PollDto;
import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
import charlie.entity.PollStateEnum;
import charlie.entity.UserEntity;
import charlie.mapper.PollEntityMapper;
import charlie.domain.Result;
import charlie.entity.PollParticipantEntity;
import charlie.utils.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PollService {

    @EJB
    private PollEntityMapper pollEntityMapper;

    @EJB
    private PollAccess pollDao;

    @EJB
    private UserService userService;

    @EJB
    private PollOwnerAccess pollOwnerDao;

    @EJB
    private PollEntityMapper entityMapper;

    @EJB
    private PollParticipantAccess pollParticipantDao;

    public Result<PollDto> save(PollDto domain) {
        if (domain == null) {
            return Result.error("Cannot accept null values");
        }

        domain.setState(PollStateEnum.PREPARED);
        domain.setUuid(UUID.randomUUID().toString());

        PollEntity entity = pollEntityMapper.toEntity(domain);

        if (entity.getStartsAt() == null || entity.getEndsAt() == null) {
            return Result.error("Date cannot be null or empty");
        }

        if (entity.getStartsAt().before(new Date())) {
            return Result.error(String.format("Start date cannot be of past date: %s", domain.getStartsAt()));
        }

        if (entity.getEndsAt().before(new Date())) {
            return Result.error(String.format("End date cannot be of past date: %s", domain.getEndsAt()));
        }

        if (entity.getEndsAt().before(entity.getStartsAt())) {
            return Result.error("End date cannot be before start date");
        }

        try {
            UserEntity userEntity = userService.getCurrentLoggedInUser();
            if (userEntity == null) {
                return Result.error("Session not started. Please login");
            }

            PollEntity savedEntity = pollDao.create(entity);

            // saving poll organizer info
            PollOwnerEntity pollOwner = new PollOwnerEntity(true);
            pollOwner.setOrganizer(userEntity);
            pollOwner.setPoll(savedEntity);
            pollOwner.setPrimaryOrganizer(Boolean.TRUE);
            pollOwnerDao.create(pollOwner);

            return Result.ok(pollEntityMapper.toDto(savedEntity));
        } catch (Exception e) {
            if (StringUtils.hasText(e.getMessage()) && e.getMessage().contains("SQLIntegrityConstraintViolationException")) {
                return Result.error(String.format("%s already exists", domain.getTitle()));
            }
            return Result.error(e.getMessage());
        }
    }

    public Result<String> changePollStateToStarted(Integer id) {
        PollEntity entity = this.pollDao.find(id);
        if (entity == null) {
            return Result.error(String.format("poll not found having id: %d", id));
        }

        if (!entity.getState().equals(PollStateEnum.PREPARED)) {
            return Result.error("Only poll in state PREPARED is allowed to change its state to STARTED");
        }

        Long pollParticipantCount = pollParticipantDao.countPollParticipantByPoll(entity);
        if (pollParticipantCount < 3) {
            return Result.error("Cannot change poll state to STARTED if poll participant is less than 3");
        }

        entity.setState(PollStateEnum.STARTED);
        pollDao.edit(entity);

        List<PollParticipantEntity> pollParticipantList = pollParticipantDao.findAllByPoll(entity);
        
        // running this into async because we don't want to block the main thread
        pollParticipantList.stream().forEach(pollParticipant -> CompletableFuture.runAsync(() -> {
               pollParticipant.setToken(UUID.randomUUID().toString().replace("-", ""));
               pollParticipantDao.edit(pollParticipant);
               
               // TODO: send email
               System.out.println("sending email to: " + pollParticipant.getEmail());
        }));
        
        return Result.ok("Changed poll state to STARTED");
    }

    public Page<PollDto> getAllWithPagination(PollPaginationRequest request) {
        PollSearchFilter filter = new PollSearchFilter();
        filter.setPageNumber(request.getPageNumber());
        filter.setPageSize(request.getPageSize());
        filter.setSortOrder(SearchOrderEnum.resolveSearchOrder(request.getSortOrder()));
        filter.setSortField(request.getSortBy());
        filter.setOrganizer(userService.getCurrentLoggedInUser());
        filter.setFilterPrimaryOrganizer(request.getFilterPostOwner());

        // TODO:
        System.out.println(filter);
        Page<PollEntity> pollPageEntities = pollDao.findAll(filter);
        System.out.println(pollPageEntities);
        List<PollDto> pollDtos = entityMapper.toDomainList(pollPageEntities.getData());

        return Page.build(pollPageEntities.getTotalCount(), pollPageEntities.getPageSize(), pollPageEntities.getPageNumber(), pollDtos);
    }

    public void deleteById(int id) {
        pollDao.deleteById(id);
    }
}

package charlie.logic.impl;

import charlie.dao.ParticipantQuestionAnswerAccess;
import charlie.dao.PollAccess;
import charlie.dao.PollOwnerAccess;
import charlie.dao.PollParticipantAccess;
import charlie.dao.PollQuestionAnswerAccess;
import charlie.dao.QuestionAnswerChoiceAccess;
import charlie.dao.UserAccess;
import charlie.dao.filter.PollSearchFilter;
import charlie.dao.filter.SearchOrderEnum;
import charlie.domain.Page;
import charlie.domain.PollPaginationRequest;
import charlie.domain.Result;
import charlie.dto.MailPollDescriptionDto;
import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.PollParticipantDto;
import charlie.dto.PollQuestionAnswerDto;
import charlie.dto.QuestionDto;
import charlie.dto.UserDto;
import charlie.entity.PollEntity;
import charlie.entity.PollOwnerEntity;
import charlie.entity.PollParticipantEntity;
import charlie.entity.PollStateEnum;
import charlie.entity.UserEntity;
import charlie.logic.PollLogic;
import charlie.logic.PollOwnerLogic;
import charlie.logic.PollParticipantLogic;
import charlie.logic.QuestionAnswerChoiceLogic;
import charlie.logic.QuestionLogic;
import charlie.logic.UserLogic;
import charlie.mapper.PollEntityMapper;
import charlie.mapper.PollOwnerEntityMapper;
import charlie.mapper.PollQuestionAnswerEntityMapper;
import charlie.service.MailService;
import charlie.service.UserService;
import charlie.utils.StringUtils;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Stateless
public class PollLogicImpl implements PollLogic {

    private static final Logger LOG = Logger.getLogger(PollLogicImpl.class.getName());

    private final String EVS_VOTING_URL = "http://localhost:8080/EVS-war/pages/participant/voting-page.xhtml?token=%s";
    private final String EVS_RESULT_URL = "http://localhost:8080/EVS-war/pages/participant/result_page.xhtml?pollUuid=%s";

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

    @EJB
    private MailService mailService;

    @EJB
    private PollOwnerEntityMapper pollOwnerEntityMapper;
    
    @EJB
    private UserAccess userDao;
    
    @EJB
    private PollQuestionAnswerAccess pollQuestionAnswerDao;
    
    @EJB
    private PollQuestionAnswerEntityMapper pollQuestionAnswerMapper;
    
    @EJB
    private PollOwnerLogic pol;
    
    @EJB
    private PollParticipantLogic ppl;
    
    @EJB
    private ParticipantQuestionAnswerAccess pqaa;
    
    @EJB
    private QuestionAnswerChoiceAccess qaca;
    
    @EJB
    private UserLogic ul;


    @Override
    public PollDto getPollById(int id) {
        PollEntity pe = pollDao.getPollById(id);
        return pollEntityMapper.toDto(pe);

    }

    @Override
    public PollDto getPollForEdit(String uuid) {
        UserDto caller = ul.getCurrentUser();
        PollEntity pe = pollDao.getPollForEdit(uuid);
        if (pe == null) {
           
            return null;
        }
        PollOwnerDto pod = pol.getPollByOrganizer(caller, pollEntityMapper.toDto(pe));
        if (pod == null || !caller.getId().equals(pod.getOrganizerId())) {
            return null;
        }
        return pollEntityMapper.toDto(pe);
    }
    
    @Override
    public PollDto getPollByUuid(String uuid){
        PollEntity pe = pollDao.getPollForEdit(uuid);
        System.out.println("POll Entity" + pe);
        return pollEntityMapper.toDto(pe);
    }

    @Override
    public void updatePoll(PollDto pollDto) {
        System.out.println("PollDTO:" + pollDto);
        PollEntity pe = pollEntityMapper.toEntity(pollDto);
        pollDao.updatePoll(pe);
    }

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
            savedEntity.setPollOwners(Set.of(pollOwner));

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

            try {
                var poll = pollParticipant.getPoll();
                MailPollDescriptionDto descriptionDto = new MailPollDescriptionDto()
                        .setEmail(pollParticipant.getEmail())
                        .setStartsAt(poll.getStartsAt().toString())
                        .setEndsAt(poll.getEndsAt().toString())
                        .setTitle(poll.getTitle())
                        .setToken(pollParticipant.getToken())
                        .setUuid(poll.getUuid());

                LOG.log(Level.INFO, "sending email to {0}", pollParticipant.getEmail());
                LOG.log(Level.INFO, "MailPollDescriptionDto {0}", descriptionDto);
                String message = buildMailMessage(descriptionDto);
                LOG.log(Level.INFO, "sending message as {0}", message);
                mailService.sendMail(pollParticipant.getEmail(), "You are invited to vote in a poll", message);
            } catch (MessagingException ex) {
                LOG.log(Level.INFO, String.format("Got exception while sending email to %s, message %s", pollParticipant.getEmail(), ex.getMessage()));
            }
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
        List<PollDto> pollDtos = entityMapper.toDomainList(pollPageEntities.getData());

        return Page.build(pollPageEntities.getTotalCount(), pollPageEntities.getPageSize(), pollPageEntities.getPageNumber(), pollDtos);
    }
    
    public void deleteById(int id) {
        pollDao.deleteById(id);
    }

    private String buildMailMessage(MailPollDescriptionDto descriptionDto) {

        StringBuilder sb = new StringBuilder();
        sb.append("Hi, ");
        sb.append(descriptionDto.getEmail());
        sb.append("\n Please find the poll details below \n");
        sb.append("<b>Poll title:</b> ");
        sb.append(descriptionDto.getTitle());
        sb.append("\n <b>Poll start date:</b> ");
        sb.append(descriptionDto.getStartsAt());
        sb.append("\n <b>Poll end date:</b> ");
        sb.append(descriptionDto.getEndsAt());
        sb.append("\n <b>Poll Secret:</b> ");
        sb.append(descriptionDto.getToken());
        sb.append(" <i style='color:red'>Note: Please don't share this to any one</i>");
        sb.append("\n <b>Poll URL:</b> ");
        sb.append(String.format(EVS_VOTING_URL, descriptionDto.getToken()));

        return sb.toString();
    }

    @Override
    public Result<PollDto> getPollByUUID(String uuid) {
        PollEntity pe = pollDao.getPollForEdit(uuid);
        if (pe == null) {
            return Result.error("poll not found by " + uuid);
        }

        return Result.ok(pollEntityMapper.toDto(pe));
    }

    @Override
    public List<PollOwnerDto> getPollOwners(int pollId) {
        var poll = pollDao.find(pollId);
        if (poll == null) {
            return Collections.emptyList();
        }
        var entities = pollOwnerDao.findAllByPoll(poll);
        return pollOwnerEntityMapper.toDomainList(entities);
    }

    @Override
    public void deletePollOrganizerById(int id) {
        pollOwnerDao.deleteById(id);
    }

    @Override
    public Result<?> addOrganizerToPoll(Integer pollId, Integer organizerId) {
        var poll = pollDao.find(pollId);
        if(poll == null)
            return Result.error("cannot find poll");
        
        var user = userDao.find(organizerId);
        if(user == null)
            return Result.error("cannot find organizer");
        
        PollOwnerEntity pollOwnerEntity = new PollOwnerEntity(true);
        pollOwnerEntity.setOrganizer(user);
        pollOwnerEntity.setPoll(poll);
        pollOwnerEntity.setPrimaryOrganizer(Boolean.FALSE);
        
        pollOwnerDao.create(pollOwnerEntity);
        return Result.ok("created successfully");
    }

    @Override
    public List<PollQuestionAnswerDto> getPollQuestionsByPollId(Integer pollId) {
        var results = pollQuestionAnswerDao.getPollQuestionsByPollId(pollId);
        System.out.println("results: " + results);
        if(results == null || results.isEmpty())
            return Collections.emptyList();
               
        return pollQuestionAnswerMapper.toDtoList(results);
    }
    
    @Override
    public void deletePollWithSingleOrganizer(PollOwnerDto pollOwnerDto){
        pollDao.deletePollBySingleOwner(pollOwnerDto);
    }
    
    @Override
    public void deletePollbyPollId(PollDto pollDto){
        pollDao.remove(entityMapper.toEntity(pollDto));
    }
    
    @Override
    public List<PollDto> findAllPolls(){
        List<PollEntity> listOfPolls = pollDao.findAll();
        return listOfPolls.stream().map(entityMapper::toDto).collect(Collectors.toList());
    }

    public void deletePollAdmin(int pollId, String pollState) {

        this.deletePollInfo(pollId);

    }

    private List<String> getAllRecipientsForPoll(int pollId, PollDto pollDto) {
        List<String> pollParticipantEmails = ppl.getParticipantsOfPoll(pollDto)
                                                .stream()
                                                .map(PollParticipantDto::getEmail)
                                                .collect(Collectors.toList());

        List<String> organizerEmails = pol.findAllByPollId(pollId)
                                           .stream()
                                           .map(PollOwnerDto::getUsername)
                                           .collect(Collectors.toList());

        pollParticipantEmails.addAll(organizerEmails);
        return pollParticipantEmails;
    }
    
    @Asynchronous
    private void sendPollDeletionNotification(List<String> recipients, String pollTitle) {
        String subject = "Poll Deletion Notification";
        String body = "The poll titled '" + pollTitle + "' has been deleted. Thank you for your participation.";

         recipients.stream().forEach(recipient -> CompletableFuture.runAsync(() -> {
            try {
                mailService.sendMail(recipient, subject, body);
            } catch (MessagingException ex) {
                LOG.log(Level.WARNING, String.format("Got exception while sending email to %s. Message: %s", recipient, ex.getMessage()), ex);
            }
        }));
    }
    
    @Asynchronous
    private void sendPollResultNotification(List<String> recipients, String pollTitle, String pollUuid){
        String subject = "Poll's Result Published!!";
        String body = "The result of poll titled '" + pollTitle + "' has been published. Please refer to the link to view the result: "
                + "http://localhost:8080/EVS-war/pages/participant/poll_result.xhtml?pollUuid=" + pollUuid;
        
        recipients.stream().forEach(recipient -> CompletableFuture.runAsync(() -> {
            try {
                mailService.sendMail(recipient, subject, body);
            } catch (MessagingException ex) {
                LOG.log(Level.WARNING, String.format("Got exception while sending email to %s. Message: %s", recipient, ex.getMessage()), ex);
            }
        }));
    }
    
    @Override
    public List<PollDto> getPollsByIdAndState(int id, PollStateEnum state){
        List<PollEntity> pe = pollDao.findAllByIdAndState(id, state);
        return pe.stream().map(entityMapper::toDto).collect(Collectors.toList());
    }
    
    
    @Override
    public void publishPollResult(int pollId){
        PollDto pollDto = getPollById(pollId);

        if(pollDto == null) {
            LOG.log(Level.WARNING, "No poll found with the provided pollId.");
            return;
        }

        List<String> allRecipients = getAllRecipientsForPoll(pollId, pollDto);
        System.out.println("All Recipients" + allRecipients);
        
        sendPollResultNotification(allRecipients, pollDto.getTitle(), pollDto.getUuid());
        
    }

    @Transactional
    @Override
    public void deletePollInfo(int pollId) {
       var pollDto = this.getPollById(pollId);       
       
       if(pollDto == null)
           return;
       
       List<String> allRecipients = getAllRecipientsForPoll(pollId, pollDto);
       System.out.println("All Recipients" + allRecipients);
       
       pol.deleteByPoll(pollDto);
       ppl.deleteByPoll(pollDto); 
       
       var participantResponses = pqaa.getParticipantQuestionAnswerIdsByPollId(pollId);
        System.out.println("participantResponses: " + participantResponses);
       LOG.log(Level.INFO, "deleting participant responses with ids: " + participantResponses);
       if(participantResponses != null) {
           participantResponses.stream().forEach(resp -> pqaa.deleteById(resp));
       }
        
       var questions = pollQuestionAnswerDao.getPollQuestionsByPollId(pollId);
       if(questions != null && !questions.isEmpty()) {           
           questions.stream().forEach(question -> {
               LOG.log(Level.INFO, "deleting question with id: " + question.getId());
               qaca.deleteByQuestionId(question.getId());
               pollQuestionAnswerDao.deleteById(question.getId());
           });
       }
       
       this.deleteById(pollId);

        if(pollDto.getState().equals(PollStateEnum.VOTING) || pollDto.getState().equals(PollStateEnum.STARTED)) {
            sendPollDeletionNotification(allRecipients, pollDto.getTitle());
        }
       
    }

}

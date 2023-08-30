package charlie.logic.impl;

import charlie.dao.QuestionAccess;
import charlie.dao.PollAccess;
import charlie.logic.QuestionLogic;
import charlie.domain.Result;
import charlie.dto.PollDto;
import charlie.dto.QuestionDto;
import charlie.entity.PollEntity;
import charlie.entity.PollQuestionEntity;
import charlie.entity.QuestionTypeEnum;
import charlie.entity.UserEntity;
import charlie.logic.QuestionLogic;
import charlie.mapper.PollEntityMapper;
import charlie.mapper.QuestionEntityMapper;
import charlie.service.UserService;
import charlie.utils.StringUtils;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

@Stateless
public class QuestionLogicImpl implements QuestionLogic{
    
    private static final Logger LOG = Logger.getLogger(QuestionLogicImpl.class.getName());
    
    @EJB
    private QuestionEntityMapper questionEntityMapper;
    
    @EJB
    private QuestionAccess questionDao;
    
    @EJB
    private UserService userService;
    
    
    @EJB
    private PollEntityMapper pollEntityMapper;

  
        
    
    @Override
    public QuestionDto getQuestionById(int id) {
        PollQuestionEntity qe = questionDao.find(id);
        return questionEntityMapper.toDto(qe);
    }
    
    @Override
    public QuestionDto getQuestionForEdit(String uuid) {
        
        PollQuestionEntity qe = questionDao.getQuestionForEdit(uuid);
        System.out.println("Question Entity" + qe);
        return questionEntityMapper.toDto(qe);
    }
    
    @Override
    public Result<QuestionDto> getQuestionByUuid(String uuid) {
        PollQuestionEntity qe = questionDao.getQuestionForEdit(uuid);
        if (qe == null) {
            return Result.error("question not found by " + uuid);
        }
        
        return Result.ok(questionEntityMapper.toDto(qe));
    }
            
    /*@Override
    public void updateQuestion(QuestionDto questionDto) {
        
    }*/
   
    public Result<QuestionDto> addQuestion (QuestionDto domain) {
        if (domain == null) {
            return Result.error("Cannot accept null values");
        }
        domain.setUuid(UUID.randomUUID().toString());
        
        PollQuestionEntity entity = questionEntityMapper.toEntity(domain);
        
        try {
            UserEntity userEntity = userService.getCurrentLoggedInUser();
            if (userEntity == null) {
                return Result.error("Session not started. Please login");
            }
            
            PollQuestionEntity savedEntity = questionDao.create(entity);
            
            //PollEntity poll = new PollEntity(true); //TODO: finish
            
            return Result.ok(questionEntityMapper.toDto(savedEntity));
        } catch (Exception e) {
            if (StringUtils.hasText(e.getMessage()) && e.getMessage().contains("SQLIntegrityConstraintViolationException")) {
                return Result.error(String.format("%s already exists",domain.getTitle()));
            }
            return Result.error(e.getMessage());
        }
    }
    
    @Override
    public int addNewQuestion(QuestionDto questionDto){
        PollQuestionEntity pq = questionDao.create(questionEntityMapper.toEntity(questionDto));
        int questionId = pq.getId();
        return questionId;
    }
    
    @Override
    public List<QuestionDto> getQuestionByPoll(PollDto pollDto){
        List<PollQuestionEntity> pqe = questionDao.findAllByPoll(pollEntityMapper.toEntity(pollDto));
        return pqe.stream().map(questionEntityMapper::toDto).collect(Collectors.toList());
    }
}
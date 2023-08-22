package charlie.logic.impl;

import charlie.dao.QuestionAccess;
import charlie.logic.QuestionLogic;
import charlie.domain.Result;
import charlie.dto.QuestionDto;
import charlie.entity.PollEntity;
import charlie.entity.PollQuestionEntity;
import charlie.entity.QuestionTypeEnum;
import charlie.entity.UserEntity;
import charlie.logic.QuestionLogic;
import charlie.mapper.QuestionEntityMapper;
import charlie.service.UserService;
import charlie.utils.StringUtils;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private UserService userService;//TODO: WHAT does this do
    
    @Override
    public QuestionDto getQuestionById(int id) {
        PollQuestionEntity qe = questionDao.getQuestionById(id);
        return questionEntityMapper.toDto(qe);
    }
    
    @Override
    public QuestionDto getQuestionForEdit(String uuid) {
        
        PollQuestionEntity qe = questionDao.getQuestionForEdit(uuid);
        System.out.println("QUESTION Entity" + qe);
        return questionEntityMapper.toDto(qe);
    }
    
    @Override
    public void updateQuestion(QuestionDto questionDto) {
        PollQuestionEntity qe = questionEntityMapper.toEntity(questionDto);
        questionDao.updateQuestion(qe);
    }
   
    public Result<QuestionDto> save (QuestionDto domain) {
        if (domain == null) {
            return Result.error("Cannot accept null values");//TODO: check if correct
        }
        
        domain.setUuid(UUID.randomUUID().toString());
        
        PollQuestionEntity entity = questionEntityMapper.toEntity(domain);
        
        //TODO: if cases for errors
        
        try {
            UserEntity userEntity = userService.getCurrentLoggedInUser();
            if (userEntity == null) {
                return Result.error("Session not started. Please login");
            }
            
            PollQuestionEntity savedEntity = questionDao.create(entity);
            
            //PollEntity poll = new PollEntity(true); TODO: finish
            
            return Result.ok(questionEntityMapper.toDto(savedEntity));
        } catch (Exception e) {
            if (StringUtils.hasText(e.getMessage()) && e.getMessage().contains("SQLIntegrityConstraintViolationException")) {
                return Result.error(String.format("%s already exists",domain.getTitle()));
            }
            return Result.error(e.getMessage());
        }
    }
    
    public void deleteById(int id) {
        questionDao.deleteById(id);
    }
    
}















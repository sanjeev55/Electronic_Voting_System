package charlie.logic;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.domain.Result;
import charlie.dto.QuestionDto;
import charlie.dto.PollDto;

import java.util.List;

import javax.ejb.Remote;
        
@Remote
public interface QuestionLogic {
    
    public static final String SINGLE_CHOICE = "SINGLE";
    public static final String MULTIPLE_CHOICE = "MULTIPLE";
    public static final String YES_NO = "YES_NO";

    // QUESTION SECTION
    public QuestionDto getQuestionById(int id);
    
    public QuestionDto getQuestionForEdit(String uuid);
    
    public Result<QuestionDto> addQuestion(QuestionDto domain);
    
    public Result<QuestionDto> getQuestionByUuid (String uuid);
    
    public int addNewQuestion(QuestionDto questionDto);
    
    public List<QuestionDto> getQuestionByPoll(PollDto pollDto);
    
}

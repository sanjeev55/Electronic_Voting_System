package charlie.logic;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.domain.Result;
import charlie.dto.QuestionDto;

import javax.ejb.Remote;
        
@Remote
public interface QuestionLogic {
    QuestionDto getQuestionById(int id);
    
    QuestionDto getQuestionForEdit(String uuid);
    
    void updateQuestion(QuestionDto questionDto);
    
    Result<QuestionDto> save(QuestionDto domain);
    
    void deleteById(int id);
    
}

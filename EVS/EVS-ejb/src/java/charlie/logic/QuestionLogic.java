package charlie.logic;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.dto.QuestionDto;

import javax.ejb.Remote;
        
@Remote
public interface QuestionLogic {
    QuestionDto getQuestionById(int id);
    
    QuestionDto getQuestionForEdit(String uuid);
    
    void updateQuestion(QuestionDto questionDto);
    
    //TODO:Result<QuestionDto> save(QuestionDto domain);
    
    void deleteById(int id);
    
}

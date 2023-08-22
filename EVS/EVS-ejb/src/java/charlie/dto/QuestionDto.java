package charlie.dto;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.entity.QuestionTypeEnum;

public class QuestionDto extends AbstractDto{
    
    private String title;
    private QuestionTypeEnum questionType;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public QuestionTypeEnum getQuestionType(){
        return questionType;
    }
    
    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }
    
    @Override
    public String toString() {
        return "QuestionDto{" + "title=" + title + ",questionType=" +questionType + '}';
    }
    
}












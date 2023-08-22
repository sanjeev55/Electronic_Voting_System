package charlie.dto;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.entity.QuestionTypeEnum;

public class QuestionDto extends AbstractDto{
    
    private String title;
    private QuestionTypeEnum type;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public QuestionTypeEnum getType(){
        return type;
    }
    
    public void setType(QuestionTypeEnum type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "QuestionDto{" + "title=" + title + ",type=" +type + '}';
    }
    
}












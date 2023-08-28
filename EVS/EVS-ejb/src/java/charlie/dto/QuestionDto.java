package charlie.dto;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.entity.QuestionTypeEnum;

public class QuestionDto extends AbstractDto{
    
    private String title;
    private QuestionTypeEnum type;
    //private Integer questionId;
    private int pollId;
    private String pollUuid;
    
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
    
    public int getPollId() {
        return pollId;
    }
    
    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
    
    public String getPollUuid() {
        return pollUuid;
    }
    
    public void setPollUuid(String pollUuid) {
        this.pollUuid = pollUuid;
    }
    
    @Override
    public String toString() {
        return "QuestionDto{" 
                + "title=" + title 
                + ",type=" + type 
                + ",pollUuid" + pollUuid 
                + "}";
    }
    
}
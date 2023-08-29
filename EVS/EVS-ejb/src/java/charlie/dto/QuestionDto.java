package charlie.dto;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

import charlie.entity.QuestionTypeEnum;

public class QuestionDto extends AbstractDto{
    
    private String title;
    private QuestionTypeEnum type;
    private int pollId;
    private String name;
    private int multipleChoiceMin;
    private int multipleChoiceMax;
    private String description;
         
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMultipleChoiceMin() {
        return multipleChoiceMin;
    }

    public void setMultipleChoiceMin(int multipleChoiceMin) {
        this.multipleChoiceMin = multipleChoiceMin;
    }

    public int getMultipleChoiceMax() {
        return multipleChoiceMax;
    }

    public void setMultipleChoiceMax(int multipleChoiceMax) {
        this.multipleChoiceMax = multipleChoiceMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descrpition) {
        this.description = descrpition;
    }

    
    @Override
    public String toString() {
        return "QuestionDto{" 
                + "title=" + title 
                + ",type=" + type 
                + ",pollId=" + pollId 
                + ",description=" + description 
                + "}";
    }
    
}
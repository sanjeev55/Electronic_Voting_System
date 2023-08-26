package charlie.dto;

import charlie.dto.AbstractDto;

public class QuestionAnswerChoiceDto extends AbstractDto {
    private String inputType;
    private String inputName;
    private String shortName;
    private String description;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    @Override
    public String toString() {
        return "QuestionAnswerChoiceDto{" + "inputType=" + inputType + ", inputName=" + inputName + ", shortName=" + shortName + ", description=" + description + '}';
    }
        
}

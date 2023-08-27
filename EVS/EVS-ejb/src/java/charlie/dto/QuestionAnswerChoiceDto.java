package charlie.dto;

import charlie.dto.AbstractDto;
import java.util.Objects;

public class QuestionAnswerChoiceDto extends AbstractDto {
    private String inputType;
    private String inputName;
    private String shortName;
    private String description;
    private boolean isSelected;

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

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }    

    @Override
    public String toString() {
        return "QuestionAnswerChoiceDto{" + "inputType=" + inputType + ", inputName=" + inputName + ", shortName=" + shortName + ", description=" + description + ", isSelected=" + isSelected + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.inputType);
        hash = 67 * hash + Objects.hashCode(this.inputName);
        hash = 67 * hash + Objects.hashCode(this.shortName);
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + (this.isSelected ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QuestionAnswerChoiceDto other = (QuestionAnswerChoiceDto) obj;
        if (this.isSelected != other.isSelected) {
            return false;
        }
        if (!Objects.equals(this.inputType, other.inputType)) {
            return false;
        }
        if (!Objects.equals(this.inputName, other.inputName)) {
            return false;
        }
        if (!Objects.equals(this.shortName, other.shortName)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }

        
}

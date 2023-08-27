package charlie.dto;

import java.util.List;

public class PollQuestionAnswerDto extends AbstractDto {
    private String title;
    private String questionType;
    private String description;
    private int multipleChoiceMin;
    private int multipleChoiceMax;
    private List<QuestionAnswerChoiceDto> answerChoices;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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

    public List<QuestionAnswerChoiceDto> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<QuestionAnswerChoiceDto> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PollQuestionAnswerDto{" + "title=" + title + ", questionType=" + questionType + ", description=" + description + ", multipleChoiceMin=" + multipleChoiceMin + ", multipleChoiceMax=" + multipleChoiceMax + ", answerChoices=" + answerChoices + '}';
    }
               
}
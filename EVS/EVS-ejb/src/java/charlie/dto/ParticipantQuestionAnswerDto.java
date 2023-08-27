package charlie.dto;

import java.util.List;

public class ParticipantQuestionAnswerDto extends AbstractDto{
    private Integer pollId;
    private Integer questionId;
    private List<Integer> answerChoiceIds;

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getAnswerChoiceIds() {
        return answerChoiceIds;
    }

    public void setAnswerChoiceIds(List<Integer> answerChoiceIds) {
        this.answerChoiceIds = answerChoiceIds;
    }

    @Override
    public String toString() {
        return "ParticipantQuestionAnswer{" + "pollId=" + pollId + ", questionId=" + questionId + ", answerChoiceIds=" + answerChoiceIds + '}';
    }
          
}

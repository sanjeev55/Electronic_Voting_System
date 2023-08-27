package charlie.dto;

import java.util.List;

public class ParticipantQuestionAnswerDto extends AbstractDto {

    private String token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ParticipantQuestionAnswerDto{" + "token=" + token + ", pollId=" + pollId + ", questionId=" + questionId + ", answerChoiceIds=" + answerChoiceIds + '}';
    }

}

package charlie.domain;

import java.util.UUID;

public class ParticipantResponse {
    private Integer pollId;
    private Integer questionId;
    private Integer answerId;
    private String uuid;
    private Integer jpaVersion;
    
    public ParticipantResponse() {
        this.uuid = UUID.randomUUID().toString();
        this.jpaVersion = 1;
    }

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

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getJpaVersion() {
        return jpaVersion;
    }  
}

package charlie.entity;

import javax.persistence.*;

@Entity
@Table(name = "answer_choice")
public class QuestionAnswerChoiceEntity extends AbstractEntity {

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "description")
    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_question_id")
    private PollQuestionEntity pollQuestion;

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

    public PollQuestionEntity getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(PollQuestionEntity pollQuestion) {
        this.pollQuestion = pollQuestion;
    }
}

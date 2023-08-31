package charlie.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "answer_choice")
@NamedQueries({
    @NamedQuery(name="getAllByQuestionId", query="SELECT qac FROM QuestionAnswerChoiceEntity qac WHERE qac.pollQuestion.id = :questionId"),
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "deleteByQuestionId", query = "delete from answer_choice where fk_question_id = ?")
})
public class QuestionAnswerChoiceEntity extends AbstractEntity implements Serializable {

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

    @Override
    public String toString() {
        return "QuestionAnswerChoiceEntity{" + "shortName=" + shortName + ", description=" + description + '}';
    }
}

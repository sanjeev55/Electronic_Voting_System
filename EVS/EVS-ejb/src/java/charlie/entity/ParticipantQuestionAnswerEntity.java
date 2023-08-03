package charlie.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "participant_question_answer")
public class ParticipantQuestionAnswerEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "fk_answer_id")
    private QuestionAnswerChoiceEntity answer;

    @ManyToOne
    @JoinColumn(name = "fk_question_id")
    private PollQuestionEntity question;

    public QuestionAnswerChoiceEntity getAnswer() {
        return answer;
    }

    public void setAnswer(QuestionAnswerChoiceEntity answer) {
        this.answer = answer;
    }

    public PollQuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(PollQuestionEntity question) {
        this.question = question;
    }
}

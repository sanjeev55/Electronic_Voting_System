package charlie.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "participant_question_answer")
@NamedNativeQueries({
    @NamedNativeQuery(name = "saveParticipantResponse", query = "insert into participant_question_answer(fk_question_id, fk_answer_id, fk_poll_id, uuid, jpa_version) values (?, ?, ?, ?, ?)"),
    @NamedNativeQuery(name = "getParticipantQuestionAnswerIdsByPollId", query = "select id from participant_question_answer where fk_poll_id = ?"),
    @NamedNativeQuery(name = "deleteByParticipantQuestionAnswerById", query = "delete from participant_question_answer where id = ?")
})
@NamedQueries({
    @NamedQuery(name="getAllByPoll", query="SELECT pqa FROM ParticipantQuestionAnswerEntity pqa WHERE pqa.poll = :poll"),
})
public class ParticipantQuestionAnswerEntity extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "fk_answer_id")
    private QuestionAnswerChoiceEntity answer;

    @ManyToOne
    @JoinColumn(name = "fk_question_id")
    private PollQuestionEntity question;
    
    @ManyToOne
    @JoinColumn(name = "fk_poll_id")
    private PollEntity poll;

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }    

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

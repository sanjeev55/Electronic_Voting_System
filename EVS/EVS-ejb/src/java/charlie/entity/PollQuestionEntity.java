package charlie.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "question")
@NamedQueries({
    @NamedQuery(name = "getPollQuestionByPoll", query = "select q from PollQuestionEntity q where q.poll = :poll"),
    @NamedQuery(name= "findAllQuestionsByOrganizer", query="SELECT q FROM PollQuestionEntity q JOIN q.poll p JOIN p.pollOwners po WHERE po.organizer = :organizer ")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "deletePollQuestionById", query = "delete from question where id = ?")
})
public class PollQuestionEntity extends AbstractEntity implements Serializable {

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionTypeEnum type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "multiple_choice_min", columnDefinition = "int default 2")
    private Integer multipleChoiceMin;

    @Column(name = "multiple_choice_max", columnDefinition = "int default 2")
    private Integer multipleChoiceMax;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_poll_id")
    private PollEntity poll;

    @OneToMany(mappedBy = "pollQuestion", fetch = FetchType.EAGER)
    private Set<QuestionAnswerChoiceEntity> answerChoices;

    public QuestionTypeEnum getType() {
        return type;
    }

    public void setType(QuestionTypeEnum type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMultipleChoiceMin() {
        return multipleChoiceMin;
    }

    public void setMultipleChoiceMin(Integer multipleChoiceMin) {
        this.multipleChoiceMin = multipleChoiceMin;
    }

    public Integer getMultipleChoiceMax() {
        return multipleChoiceMax;
    }

    public void setMultipleChoiceMax(Integer multipleChoiceMax) {
        this.multipleChoiceMax = multipleChoiceMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }

    public Set<QuestionAnswerChoiceEntity> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(Set<QuestionAnswerChoiceEntity> answerChoices) {
        this.answerChoices = answerChoices;
    }

    @Override
    public String toString() {
        return "PollQuestionEntity{" + "type=" + type + 
                ", title=" + title + 
                ", answerChoices=" + answerChoices + 
                '}';
    }
    
}

package charlie.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "question")
public class PollQuestionEntity extends AbstractEntity {

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionTypeEnum type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "multiple_choice_min", columnDefinition = "int default 1")
    private Integer multipleChoiceMin;

    @Column(name = "multiple_choice_max", columnDefinition = "int default 1")
    private Integer multipleChoiceMax;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_poll_id")
    private PollEntity poll;

    @OneToMany(mappedBy = "pollQuestion")
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
}
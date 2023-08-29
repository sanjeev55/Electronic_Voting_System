package charlie.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "poll", indexes = {
    @Index(name = "starts_ends_at_idx", columnList = "starts_at, ends_at"),
    @Index(name = "poll_state_idx", columnList = "state")
})
@NamedQueries({
    @NamedQuery(name = "deleteById", query = "DELETE FROM PollEntity p WHERE p.id = :id"),
    @NamedQuery(name = "getPollById", query = "SELECT p FROM PollEntity p WHERE p.id = :id"),
    @NamedQuery(name = "getPollByUuid", query = "SELECT p FROM PollEntity p WHERE p.uuid = :uuid"),
    @NamedQuery(name = "getStartedPollsInDateRange", query = "select p from PollEntity p where p.state = :state and p.startsAt >= :from and p.startsAt < :to"),
    @NamedQuery(name = "getVotingPollsInDateRange", query = "select p from PollEntity p where p.state = :state and p.endsAt >= :from and p.endsAt < :to"),
    @NamedQuery(
    name="deletePollBySingleOwner",
    query="DELETE FROM PollEntity p WHERE :owner MEMBER OF p.pollOwners AND SIZE(p.pollOwners) = 1"),
    @NamedQuery(name="findAllByIdAndState", query="SELECT p FROM PollEntity p WHERE p.id = :id AND p.state = :state")
})
public class PollEntity extends AbstractEntity {

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "starts_at", nullable = false, columnDefinition = "datetime")
    private Date startsAt;

    @Column(name = "ends_at", nullable = false, columnDefinition = "datetime")
    private Date endsAt;

    @Column(name = "state", nullable = false, columnDefinition = "ENUM ('PREPARED', 'STARTED', 'VOTING', 'FINISHED') DEFAULT 'PREPARED'")
    @Enumerated(EnumType.STRING)
    private PollStateEnum state;

    @Column(name = "track_participant", nullable = false, columnDefinition = "boolean default false")
    private Boolean trackParticipant;

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY)
    private Set<PollOwnerEntity> pollOwners;

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY)
    private Set<PollQuestionEntity> pollQuestions;
   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Date startsAt) {
        this.startsAt = startsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public PollStateEnum getState() {
        return state;
    }

    public void setState(PollStateEnum state) {
        this.state = state;
    }

    public Boolean getTrackParticipant() {
        return trackParticipant;
    }

    public void setTrackParticipant(Boolean trackParticipant) {
        this.trackParticipant = trackParticipant;
    }

    public Set<PollOwnerEntity> getPollOwners() {
        return pollOwners;
    }

    public void setPollOwners(Set<PollOwnerEntity> pollOwners) {
        this.pollOwners = pollOwners;
    }

    public Set<PollQuestionEntity> getPollQuestions() {
        return pollQuestions;
    }

    public void setPollQuestions(Set<PollQuestionEntity> pollQuestions) {
        this.pollQuestions = pollQuestions;
    }

    @Override
    public String toString() {
        return "PollEntity{"
                + "title='" + title + '\''
                + ", description='" + description + '\''
                + ", startsAt=" + startsAt
                + ", endsAt=" + endsAt
                + ", state=" + state
                + ", trackParticipant=" + trackParticipant
                + "} " + super.toString();
    }
}

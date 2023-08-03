package charlie.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "poll", indexes = {
    @Index(name = "starts_ends_at_idx", columnList = "starts_at, ends_at"),
    @Index(name = "poll_state_idx", columnList = "state")
})
public class PollEntity extends AbstractEntity {

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "starts_at", nullable = false, columnDefinition = "datetime")
    private Instant startsAt;

    @Column(name = "ends_at", nullable = false, columnDefinition = "datetime")
    private Instant endsAt;

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

    public Instant getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Instant startsAt) {
        this.startsAt = startsAt;
    }

    public Instant getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Instant endsAt) {
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
                + ", pollOwners=" + pollOwners
                + "} " + super.toString();
    }
}

package charlie.entity;

import javax.persistence.*;

@Entity
@Table(name = "poll_participant")
public class PollParticipantEntity extends AbstractEntity {

    @Column(name = "token", nullable = false, updatable = false, unique = true)
    private String token;

    @Column(name = "has_participated", columnDefinition = "boolean default false")
    private Boolean hasParticipated;

    @OneToOne
    @JoinColumn(name = "fk_participant_list_id")
    private ParticipantEntity participant;

    @ManyToOne
    @JoinColumn(name = "fk_poll_id")
    private PollEntity poll;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getHasParticipated() {
        return hasParticipated;
    }

    public void setHasParticipated(Boolean hasParticipated) {
        this.hasParticipated = hasParticipated;
    }

    public ParticipantEntity getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantEntity participant) {
        this.participant = participant;
    }

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }
}

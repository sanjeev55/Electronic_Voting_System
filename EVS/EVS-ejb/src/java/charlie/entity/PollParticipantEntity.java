package charlie.entity;

import javax.persistence.*;

@Entity
@Table(name = "poll_participant")
public class PollParticipantEntity extends AbstractEntity {

    @Column(name = "token", nullable = false, updatable = false, unique = true)
    private String token;

    @Column(name = "has_participated", columnDefinition = "boolean default false")
    private Boolean hasParticipated;

    @Column(name = "email", nullable = false)
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }
}

package charlie.entity;

import javax.persistence.*;

@Entity
@Table(name = "poll_participant")
@NamedQueries(value = {
    @NamedQuery(name = "countPollParticipantsByPollId", query = "select count(pp.id) from PollParticipantEntity pp where pp.poll = :poll"),
    @NamedQuery(name = "getPollParticipantsByPoll", query = "select pp from PollParticipantEntity pp where pp.poll = :poll"),
    @NamedQuery(name = "getPollParticipantByEmail", query = "SELECT p from PollParticipantEntity p where p.email = :email"),
})
public class PollParticipantEntity extends AbstractEntity {

    @Column(name = "token", nullable = false, unique = true)
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

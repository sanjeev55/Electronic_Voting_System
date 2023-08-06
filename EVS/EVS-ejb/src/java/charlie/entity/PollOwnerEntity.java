package charlie.entity;

import javax.persistence.*;

@Entity
@Table(name = "poll_owner")
@NamedNativeQueries({
    @NamedNativeQuery(name = "deleteByPollId", query = "delete from poll_owner where poll_id = ?")
})
public class PollOwnerEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private UserEntity organizer;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private PollEntity poll;

    @Column(name = "is_primary_organizer", columnDefinition = "boolean default false")
    private Boolean isPrimaryOrganizer;
    
    public PollOwnerEntity() {
        
    }
    
    public PollOwnerEntity(boolean isNew) {
        super(isNew);
    }

    public UserEntity getOrganizer() {
        return organizer;
    }

    public void setOrganizer(UserEntity organizer) {
        this.organizer = organizer;
    }

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }

    public Boolean getPrimaryOrganizer() {
        return isPrimaryOrganizer;
    }

    public void setPrimaryOrganizer(Boolean primaryOrganizer) {
        isPrimaryOrganizer = primaryOrganizer;
    }

    @Override
    public String toString() {
        return "PollOwner{"
                + "organizer=" + organizer
                + ", poll=" + poll
                + ", isPrimaryOrganizer=" + isPrimaryOrganizer
                + "} " + super.toString();
    }
}

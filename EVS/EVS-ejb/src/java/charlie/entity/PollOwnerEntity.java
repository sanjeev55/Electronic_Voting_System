package charlie.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "poll_owner")
@NamedNativeQueries({
    @NamedNativeQuery(name = "deleteByPollId", query = "delete from poll_owner where poll_id = ?")
})
@NamedQueries({
    @NamedQuery(name = "getByPoll", query = "select po from PollOwnerEntity po where po.poll = :poll"),
    @NamedQuery(name = "getByPollId", query = "select po from PollOwnerEntity po where po.poll.id = :pollId"),
    @NamedQuery(name = "deletePollOwnerById", query = "delete from PollOwnerEntity po where po.id = :id"),
    @NamedQuery(name="deleteByOrganizer", 
            query="DELETE FROM PollOwnerEntity po WHERE po.organizer = :organizer"),
    @NamedQuery(name = "findAllByOrganizerAndIsPrimaryOrganizer", 
            query="SELECT po FROM PollOwnerEntity po where po.organizer = :organizer AND po.isPrimaryOrganizer = :isPrimaryOrganizer"),
    @NamedQuery(name="deleteAllByPoll", query="DELETE FROM PollOwnerEntity po WHERE po.poll = :poll"),
    @NamedQuery(name="findAllByOrganizerAndPollState", query ="SELECT po FROM PollOwnerEntity po WHERE po.organizer = :organizer AND po.poll.state = :state"),
    @NamedQuery(name="findByPollAndIsPrimaryOrganizer", query="SELECT po FROM PollOwnerEntity po WHERE po.poll = :poll AND po.isPrimaryOrganizer = :isPrimaryOrganizer"),
    @NamedQuery(name="findAllByOrganizer", query="SELECT po FROM PollOwnerEntity po WHERE po.organizer=:organizer"),
    @NamedQuery(name="findPollByOrganizer", query="SELECT po FROM PollOwnerEntity po WHERE po.organizer=:organizer AND po.poll = :poll"),
})
public class PollOwnerEntity extends AbstractEntity implements Serializable {

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

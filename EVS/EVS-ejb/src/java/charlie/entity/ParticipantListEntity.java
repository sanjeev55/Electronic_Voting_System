package charlie.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "participant_list")
@NamedQueries({
    @NamedQuery(name = "findParticipantListByOrganizer", query="SELECT pl FROM ParticipantListEntity pl WHERE pl.organizer.id = :organizerId "),
    @NamedQuery(name = "deleteParicipantListByOrganizerId", query="DELETE FROM ParticipantListEntity pl WHERE pl.organizer.id = :organizerId")
})
public class ParticipantListEntity extends AbstractEntity implements Serializable {

    @Column(name = "name")
    private String name;

    // This will store the list of unique email configured by organizers in json array format
    // Don't store single email here, rather store json array filled with multiple emails.
    // example: ["abc@gmail.com", "abc2@gmail.com]
    // For convenience, use ParticipantListDto to get email from view and use ParticipantListEntityMapper to convert dto to entity before saving
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "fk_organizer_id")
    private UserEntity organizer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity getOrganizer() {
        return organizer;
    }

    public void setOrganizer(UserEntity organizer) {
        this.organizer = organizer;
    }

    @Override
    public String toString() {
        return "ParticipantListEntity{" + "name=" + name + ", email=" + email + ", organizer=" + organizer + '}';
    }
    
    
}

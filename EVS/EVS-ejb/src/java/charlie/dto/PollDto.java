package charlie.dto;

import charlie.entity.PollStateEnum;

public class PollDto extends AbstractDto{
    
    private String title;
    private String description;
    private String startsAt;
    private String endsAt;
    private PollStateEnum state;
    private Boolean trackParticipant;
    private UserDto primaryOrganizer;

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

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
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

    public UserDto getPrimaryOrganizer() {
        return primaryOrganizer;
    }

    public void setPrimaryOrganizer(UserDto primaryOrganizer) {
        this.primaryOrganizer = primaryOrganizer;
    }

    @Override
    public String toString() {
        return "PollDto{" + "title=" + title + ", description=" + description + ", startsAt=" + startsAt + ", endsAt=" + endsAt + ", state=" + state + ", trackParticipant=" + trackParticipant + ", primaryOrganizer=" + primaryOrganizer + '}';
    }
    
    
}

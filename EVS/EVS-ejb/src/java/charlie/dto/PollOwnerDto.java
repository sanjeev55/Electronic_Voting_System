package charlie.dto;

public class PollOwnerDto extends AbstractDto{
    private String username;
    private Boolean primaryOrganizer;
    private int pollId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public Boolean getPrimaryOrganizer() {
        return primaryOrganizer;
    }

    public void setPrimaryOrganizer(Boolean primaryOrganizer) {
        this.primaryOrganizer = primaryOrganizer;
    }
    
    
}

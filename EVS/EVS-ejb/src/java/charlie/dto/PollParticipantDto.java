package charlie.dto;

public class PollParticipantDto extends AbstractDto {
    private String token;
    private Boolean hasParticipated;
    private String email;
    private PollDto poll;

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

    public PollDto getPoll() {
        return poll;
    }

    public void setPoll(PollDto poll) {
        this.poll = poll;
    }
    
    
}

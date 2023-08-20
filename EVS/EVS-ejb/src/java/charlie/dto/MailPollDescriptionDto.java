package charlie.dto;

public class MailPollDescriptionDto {
    private String email;
    private String startsAt;
    private String endsAt;
    private String token;
    private String title;
    private String uuid;

    public String getEmail() {
        return email;
    }

    public MailPollDescriptionDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public MailPollDescriptionDto setStartsAt(String startsAt) {
        this.startsAt = startsAt;
        return this;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public MailPollDescriptionDto setEndsAt(String endsAt) {
        this.endsAt = endsAt;
        return this;
    }

    public String getToken() {
        return token;
    }

    public MailPollDescriptionDto setToken(String token) {
        this.token = token;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MailPollDescriptionDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public MailPollDescriptionDto setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return "MailPollDescriptionDto{" + "email=" + email + ", startsAt=" + startsAt + ", endsAt=" + endsAt + ", token=" + token + ", title=" + title + ", uuid=" + uuid + '}';
    }
    
    
}

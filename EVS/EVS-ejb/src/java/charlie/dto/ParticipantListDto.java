package charlie.dto;

import java.util.Set;


public class ParticipantListDto extends AbstractDto {
    private String name;
    private Set<String> emails;
    private Integer organizerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }  

    @Override
    public String toString() {
        return "ParticipantListDto{" + "name=" + name + ", emails=" + emails + ", organizerId=" + organizerId + '}';
    }
    
    
}

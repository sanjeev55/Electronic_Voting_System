/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.dto;

import charlie.entity.UserEntity;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
public class ParticipantDto extends AbstractDto {
    private String name;
    private String email;
    private UserDto organizer;
    private PollDto poll;

    // Constructors
    public ParticipantDto() {
    }

    public ParticipantDto(String name, String email, UserDto organizer, PollDto poll) {
        this.name = name;
        this.email = email;
        this.organizer = organizer;
        this.poll = poll;
    }

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

    public UserDto getOrganizer() {
        return organizer;
    }

    public void setOrganizer(UserDto organizer) {
        this.organizer = organizer;
    }

    public PollDto getPoll() {
        return poll;
    }

    public void setPoll(PollDto poll) {
        this.poll = poll;
    }
    
    @Override
    public String toString() {
        return "PollDto{" + "name=" + name + ", email=" + email + ", organizer=" + organizer + ", poll=" + poll + '}';
    }
}

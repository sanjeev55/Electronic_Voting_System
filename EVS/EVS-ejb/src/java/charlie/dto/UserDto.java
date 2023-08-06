package charlie.dto;

import charlie.entity.RoleEnum;

public class UserDto extends AbstractDto {

    private String username;
    private String firstName;
    private String lastName;
    private RoleEnum role;

    public UserDto() {
    }
    
    
    public UserDto(String uuid, int jpaVersion, String username, String firstName, String lastName) {
        super(uuid, jpaVersion);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDto{" + "username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", role=" + role + '}';
    }
    
}
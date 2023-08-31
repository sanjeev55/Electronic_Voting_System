package charlie.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "deleteUserById", query = "DELETE FROM UserEntity u WHERE u.id= :id"),
    @NamedQuery(name = "getUserCount", query = "SELECT COUNT(u) FROM UserEntity u"),
    @NamedQuery(name = "getUserList", query = "SELECT u FROM UserEntity u ORDER BY u.firstName, u.uuid"),
    @NamedQuery(name = "getUserByName", query = "SELECT u FROM UserEntity u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "getUserByUserName", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
    @NamedQuery(name = "getUserByUuid", query= "SELECT u from UserEntity u WHERE u.uuid = :uuid"),
    @NamedQuery(name = "getUsersByRole", query= "SELECT u from UserEntity u WHERE u.role = :role"),
}
)
public class UserEntity extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "role", nullable = false, columnDefinition = "ENUM ('ROLE_ORGANIZER', 'ROLE_ADMINISTRATOR') DEFAULT 'ROLE_ORGANIZER'")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public UserEntity() {
    }

    public UserEntity(boolean isNew) {
        super(isNew);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", role=" + role + '}';
    }

}

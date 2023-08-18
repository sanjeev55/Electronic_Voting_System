/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import charlie.dto.UserDto;
import charlie.logic.UserLogic;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "userBean")
@ManagedBean
public class UserBean {

    /**
     * Creates a new instance of UserBean
     */
    @EJB
    private UserLogic userLogic;

    private UserDto selectedUser;

    public List<UserDto> completeUser(String query) {
        return userLogic.getAllUsers().stream()
                .filter(user -> user.getUsername().startsWith(query))
                .collect(Collectors.toList());
    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }
    
}

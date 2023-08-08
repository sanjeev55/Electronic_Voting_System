/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package charlie.logic;

import charlie.dto.UserDto;
import charlie.entity.RoleEnum;
import javax.ejb.Remote;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Remote
public interface UserLogic {
    public static final String USER_ROLE = "USER";
    public static final String STAFF_ROLE = "STAFF";
    public static final String ADMIN_ROLE = "ADMIN";
    
    public UserDto getCurrentUser();
    
    public String getCurrentUsername();
    
    public void updateUserRole(String uuid, String role);
}

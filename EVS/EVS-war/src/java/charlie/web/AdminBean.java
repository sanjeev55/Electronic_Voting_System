/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

    /**
     * Creates a new instance of AdminBean
     */
    public AdminBean() {
    }
    
    public void managePolls() {
    // Your code here to manage polls
}
    
    public void manageOrganizer(){}
    
}

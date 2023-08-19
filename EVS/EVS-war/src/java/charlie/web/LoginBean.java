package charlie.web;

import charlie.dto.UserDto;
import charlie.logic.UserLogic;
import charlie.service.MailService;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(LoginBean.class.getName());

    private UserDto currentUser;
    
    @EJB
    private UserLogic ul;
    
    @EJB
    private MailService ms;
    
    @PostConstruct
    public void newSession() {
        LOG.info("EVS: NEW SESSION");
    }

    public boolean isLoggedIn() {
        return getUser() != null;
    }

    public boolean isStaff() {
        if (!isLoggedIn()) {
            return false;
        }
        return FacesContext.getCurrentInstance()
                .getExternalContext().isUserInRole("STAFF");
    }
    
    public boolean isAdmin(){
        return FacesContext.getCurrentInstance()
                .getExternalContext().isUserInRole("ADMIN");
    }

    private Principal oldPrincipal = null; // used to detect changed login

    public UserDto getUser() {
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        if (p == null) {
            currentUser = null;
        } else {
            if (!p.equals(oldPrincipal)) {
                LOG.log(Level.INFO, "EVS: LOGIN user {0}", p.getName());
                currentUser = ul.getCurrentUser();
                if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("STAFF")) {
                ul.updateUserRole(currentUser.getUuid(), "STAFF");
                } else if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMIN")){
                ul.updateUserRole(currentUser.getUuid(), "ADMIN");
                }
            }
        }
        oldPrincipal = p;
        return currentUser;
    }
    
    public void redirectBasedOnRole(){
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            
           if(context.getExternalContext().isUserInRole("ADMIN")){
               context.getExternalContext().redirect("pages/admin/home.xhtml");
           }
           else if (context.getExternalContext().isUserInRole("STAFF")){
               context.getExternalContext().redirect("pages/user/home.xhtml");
           }
           else if (context.getExternalContext().isUserInRole("USER")){
               context.getExternalContext().redirect("pages/participant/tokenInput.xhtml");
           }
           
        }
        catch(IOException e){
            System.out.println("Error:" + e);
        }
        
    }


    public void invalidateSession() {
        LOG.log(Level.INFO, "invalidateSession()");
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        if (p != null) {
            LOG.log(Level.INFO, "Contacts: LOGOUT user {0}", p.getName());
        }
        currentUser = null;
        oldPrincipal = null;
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
    }

    public void logout() {
        try {
            invalidateSession();
            
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/pages/login.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mailParticipants() throws MessagingException{
        ms.sendMail(currentUser.getUsername(), "This is test", "This is test mail for smtp");
        
    }
    
}

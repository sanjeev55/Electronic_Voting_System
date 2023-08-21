/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import charlie.dto.ParticipantListDto;
import charlie.dto.UserDto;
import charlie.logic.ParticipantListLogic;
import charlie.logic.UserLogic;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "editParticipantListBean")
@ViewScoped
public class EditParticipantListBean implements Serializable {
    @EJB
    private ParticipantListLogic pll;
    @EJB
    private UserLogic ul;
    
    private UserDto currentUser;
    
    private ParticipantListDto currentParticipantListDto;
    private String currentParticipantListId;
    private String emails;
    private boolean success;
    private boolean failure;
    private String failureMessage;
    
    @PostConstruct
    public void init(){
        getCurrentUser();
        getCurrentParticipantListDto();
    }

    public UserDto getCurrentUser() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String currentUserId = params.get("currentUserId");
        if(currentUserId != null && !currentUserId.isEmpty()) {
            currentUser = ul.getUserById(Integer.parseInt(currentUserId));
        }
        return currentUser;
    }

    public void setCurrentUser(UserDto currentUser) {
        this.currentUser = currentUser;
    }
    
    public ParticipantListDto getCurrentParticipantListDto() {
        if(currentParticipantListDto == null){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        currentParticipantListId = params.get("participantListId");
        
        
        currentParticipantListDto = pll.getByParticipantListById(Integer.parseInt(currentParticipantListId));
        emails = currentParticipantListDto.getEmails().toString().replace("[","").replace("]","");
        setCurrentParticipantListDto(currentParticipantListDto);
        }
        return currentParticipantListDto;
    }

    public void setCurrentParticipantListDto(ParticipantListDto currentParticipantListDto) {
        this.currentParticipantListDto = currentParticipantListDto;
    }

    public String getCurrentParticipantListId() {
        return currentParticipantListId;
    }

    public void setCurrentParticipantListId(String currentParticipantListId) {
        this.currentParticipantListId = currentParticipantListId;
    }
    
    public String updateParticipantList(){
        try {
            Set<String> emailSet = new HashSet<>(Arrays.asList(emails.split(",")));
            System.out.println("Emails:"+emails);
            
            // Server-side email validation
            for (String email : emailSet) {
                email=email.trim();
                if (!validateEmail(email)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format: " + email, null));
                }
            }
            currentParticipantListDto.setEmails(emailSet);
            System.out.println("Current participant list:" + currentParticipantListDto);
            pll.updateParticipantList(currentParticipantListDto);
            success = true;
            failure = false;
            currentParticipantListDto = null;
            currentParticipantListId = null;
            return "manageParticipantListPage";
        } catch (ValidatorException e) {
            success = false;
            failure = true;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            failureMessage = t.getMessage();
            return null;
        }
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
    
    private boolean validateEmail(String email) {
        email = email.trim();
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isFailure() {
        return failure;
    }

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
    
    
}

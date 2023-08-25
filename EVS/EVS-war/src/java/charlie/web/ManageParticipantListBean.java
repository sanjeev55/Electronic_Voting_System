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
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "manageParticipantListBean")
@ViewScoped
public class ManageParticipantListBean implements Serializable {
    
    @EJB
    private ParticipantListLogic pll;
    @EJB
    private UserLogic ul;
    
    private UserDto currentUser;
    
    private List<ParticipantListDto> participantListDto;
    
    private String newListEmails;
    private String newListName;
    
    private String processedEmailString;
    
    private boolean createSuccess;
    private boolean createFailure;
    
    private boolean deleteSuccess;
    private boolean deleteFailure;
    
    private String createFailureMessage;
    private String deleteFailureMessage;
    
    private String currentParticipantListId;
    
    
    
    @PostConstruct
    public void init(){
        getCurrentUser();
        getParticipantList();
        deleteSuccess = false;
        createSuccess= false;
        createFailure = false;
        deleteFailure =false;

        System.out.println("Current user:This is current user" + currentUser);
        System.out.println("ParticipantList:" + participantListDto);
    }
    
    public List<ParticipantListDto> getParticipantList(){
        if(participantListDto == null){
        participantListDto = pll.getListByOriganizer(currentUser.getId());
        System.out.println("ParticipantList:" + participantListDto);
        }
        return participantListDto;
    }
    
    public void setParticipantList(List<ParticipantListDto> participantListDto){
        this.participantListDto = participantListDto;
    }

    public UserDto getCurrentUser() {
        currentUser = ul.getCurrentUser();
        return currentUser;
    }
    
    public void setCurrentUser(UserDto currentUser){
       this.currentUser = currentUser;
    }
    
    public void createParticipantList(){
        try {
            
            if (isListNameTaken(newListName)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "List name already in use: " + newListName, null));
            }
            
            Set<String> emails = new HashSet<>(Arrays.asList(newListEmails.split(",")));
            System.out.println("Emails:"+emails);
            
            for (String email : emails) {
                email=email.trim();
                if (!validateEmail(email)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format: " + email, null));
                }
            }

            ParticipantListDto newParticipantList = new ParticipantListDto();
            newParticipantList.setName(newListName);
            newParticipantList.setEmails(emails);
            newParticipantList.setOrganizerId(currentUser.getId());
            
            System.out.println("new participant's list:" + newParticipantList);

            pll.saveParticipantList(newParticipantList);
            newListName = null;
            newListEmails = null;
            participantListDto = null;
            createSuccess = true;
            createFailure = false;
            deleteSuccess = false;
            deleteFailure = false;

        } catch (ValidatorException e) {
            createSuccess = false;
            createFailure = true;
            deleteSuccess = false;
            deleteFailure = false;
            newListName = null;
            newListEmails = null;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            createFailureMessage = t.getMessage();
        }
    }

    public String getNewListEmails() {
        return newListEmails;
    }

    public void setNewListEmails(String newListEmails) {
        this.newListEmails = newListEmails;
    }

    public String getNewListName() {
        return newListName;
    }

    public void setNewListName(String newListName) {
        this.newListName = newListName;
    }
    
    public void deleteParticipantList(){
        try{
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        currentParticipantListId = params.get("listId");
        
        System.out.println("Current list id:" + currentParticipantListId);
        pll.deleteParticipantListById(Integer.parseInt(currentParticipantListId));
        
        currentParticipantListId = null;
        participantListDto = null;
        deleteSuccess = true;
        deleteFailure = false;
        createSuccess = false;
        createFailure = false;
        }catch(EJBException e){
            deleteSuccess = false;
            deleteFailure = true;
            createSuccess = false;
            createFailure = false;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            deleteFailureMessage = t.getMessage();
        }
        
    }
    
    
    
    private boolean validateEmail(String email) {
        email = email.trim(); 
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }
    
    public boolean isCreateSuccess() {
        return createSuccess;
    }

    public boolean isCreateFailure() {
        return createFailure;
    }

    public String getCreateFailureMessage() {
        return createFailureMessage;
    }
    
    public boolean isDeleteSuccess() {
        return deleteSuccess;
    }

    public boolean isDeleteFailure() {
        return deleteFailure;
    }

    public String getDeleteFailureMessage() {
        return deleteFailureMessage;
    }

    public String getProcessedEmailString() {
        return processedEmailString;
    }

    public void setProcessedEmailString(String processedEmailString) {
        this.processedEmailString = processedEmailString;
    }
    
    private boolean isListNameTaken(String name) {
        for (ParticipantListDto list : getParticipantList()) {
            if (list.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
}

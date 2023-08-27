/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.logic.PollLogic;
import charlie.logic.PollOwnerLogic;
import charlie.logic.PollParticipantLogic;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "managePollsAdminBean")
@ViewScoped
public class ManagePollsAdminBean implements Serializable {

    
    @EJB
    private PollLogic pl;
    
    @EJB
    private PollOwnerLogic pol;
    
    @EJB
    private PollParticipantLogic ppl;
    
    private List<PollDto> listOfPolls;
    
    private boolean deleteSuccess;
    private boolean deleteFailure;
    private String deleteFailureMessage;
    
    @PostConstruct
    public void init(){
        getListOfPolls();
    }

    public List<PollDto> getListOfPolls() {
        listOfPolls = pl.findAllPolls();
        return listOfPolls;
    }

    public void setListOfPolls(List<PollDto> listOfPolls) {
        this.listOfPolls = listOfPolls;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePoll(){
        try{
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int pollId = Integer.parseInt(params.get("pollId"));
            String pollState = params.get("pollState");

            pl.deletePollAdmin(pollId, pollState);
            deleteSuccess = true;
        } catch(NumberFormatException e){
            deleteSuccess = false;
            deleteFailure = true;
            deleteFailureMessage = "Failed to delete poll due to: " + e.getMessage();
        }
        
    }
    
    public String getOwnersOfPoll(PollDto pollDto) {
        List<PollOwnerDto> pollOwners = pol.findAllByPoll(pollDto); 
        return pollOwners.stream().map(po -> po.getUsername())
                         .collect(Collectors.joining(", "));
    }

    public boolean isDeleteSuccess() {
        return deleteSuccess;
    }

    public void setDeleteSuccess(boolean deleteSuccess) {
        this.deleteSuccess = deleteSuccess;
    }

    public boolean isDeleteFailure() {
        return deleteFailure;
    }

    public void setDeleteFailure(boolean deleteFailure) {
        this.deleteFailure = deleteFailure;
    }

    public String getDeleteFailureMessage() {
        return deleteFailureMessage;
    }

    public void setDeleteFailureMessage(String deleteFailureMessage) {
        this.deleteFailureMessage = deleteFailureMessage;
    }
    
    
    
}

package charlie.web;

import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.UserDto;
import charlie.logic.PollLogic;
import charlie.logic.UserLogic;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "managePollOrganizers")
@ViewScoped
public class ManagePollOrganizers implements Serializable {

    private String currentPollUUID;

    private boolean renderPollNotFoundMessage;
    private String errorMessage;
    private PollDto currentPoll;
    private List<PollOwnerDto> pollOrganizers;
    private Integer currentPollOwnerId;
    private boolean deleteSuccess;
    private boolean deleteFailure;
    private String deleteFailureMessage;
    private String selectedOrganizer;

    @EJB
    private PollLogic pollLogic;
    @EJB
    private UserLogic userLogic;

    public void init() {
        System.out.println(currentPollUUID);
        var result = pollLogic.getPollByUUID(currentPollUUID);
        if (result.isError()) {
            this.renderPollNotFoundMessage = true;
            this.errorMessage = result.getError();
            this.currentPoll = null;
        } else {
            this.renderPollNotFoundMessage = false;
            this.errorMessage = null;
            this.currentPoll = result.getValue();
        }
    }

    public List<PollOwnerDto> getPollOrganizers() {
        if (this.pollOrganizers == null) {
            this.pollOrganizers = pollLogic.getPollOwners(currentPoll.getId());
        }

        return this.pollOrganizers;
    }

    public void deletePollOwner() {
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            currentPollOwnerId = Integer.parseInt(params.get("id"));

            if (pollOrganizers != null) {
                var filteredPollOrganizerById = pollOrganizers.stream().filter(po -> po.getId() == currentPollOwnerId).findFirst();
                if (filteredPollOrganizerById.isPresent()
                        && filteredPollOrganizerById.get().getPrimaryOrganizer().equals(Boolean.TRUE)) {
                    deleteSuccess = false;
                    deleteFailure = true;
                    deleteFailureMessage = "Cannot delete primary organizer from poll";
                    return;
                }
            }

            System.out.println("Current list id:" + currentPollOwnerId);
            pollLogic.deletePollOrganizerById(currentPollOwnerId);

            currentPollOwnerId = null;
            pollOrganizers = null;
            deleteSuccess = true;
            deleteFailure = false;
        } catch (Exception e) {
            deleteSuccess = false;
            deleteFailure = true;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            deleteFailureMessage = t.getMessage();
        }

    }
    
    public List<UserDto> getOrganizers() {
        var uu =  userLogic.getUserHavingRoleOrganizers();
        System.out.println("uu " + uu);
        return uu;
    }

    public Integer getCurrentPollOwnerId() {
        return currentPollOwnerId;
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

    public String getCurrentPollUUID() {
        return currentPollUUID;
    }

    public void setCurrentPollUUID(String currentPollUUID) {
        this.currentPollUUID = currentPollUUID;
    }

    public boolean isRenderPollNotFoundMessage() {
        return renderPollNotFoundMessage;
    }

    public void setRenderPollNotFoundMessage(boolean renderPollNotFoundMessage) {
        this.renderPollNotFoundMessage = renderPollNotFoundMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PollDto getCurrentPoll() {
        return currentPoll;
    }

    public void setCurrentPoll(PollDto currentPoll) {
        this.currentPoll = currentPoll;
    }

    public String getSelectedOrganizer() {
        return selectedOrganizer;
    }

    public void setSelectedOrganizer(String selectedOrganizer) {
        this.selectedOrganizer = selectedOrganizer;
    }
    

}

package charlie.web;

import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.UserDto;
import charlie.entity.PollStateEnum;
import charlie.logic.ParticipantListLogic;
import charlie.logic.PollLogic;
import charlie.logic.PollOwnerLogic;
import charlie.logic.PollParticipantLogic;
import charlie.logic.UserLogic;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;

@Named(value = "manageOrganizersAdminBean")
@RequestScoped
public class ManageOrganizersAdminBean implements Serializable {
    @EJB
    private UserLogic ul;
    @EJB
    private PollOwnerLogic pol;
    @EJB
    private PollParticipantLogic ppl;
    @EJB
    private ParticipantListLogic pll;
    @EJB
    private PollLogic pl;

    private List<UserDto> listOrganizerDto;
    private boolean deleteSuccess;
    private boolean deleteFailure;
    private String deleteFailureMessage;

    @PostConstruct
    public void init() {
        refreshListOrganizerDto();
    }

    public List<UserDto> getListOrganizerDto() {
        return listOrganizerDto;
    }

    public void setListOrganizerDto(List<UserDto> listOrganizerDto) {
        this.listOrganizerDto = listOrganizerDto;
    }

    private void refreshListOrganizerDto() {
        listOrganizerDto = ul.getUserHavingRoleOrganizers();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteOrganizer() {
        int organizerId = getOrganizerIdFromRequest();
        if (organizerId < 0) {
            handleError("Invalid Organizer ID provided.", "Failed to parse organizerId.");
            return;
        }

        try {
            UserDto userDto = ul.getUserById(organizerId);
            List<PollOwnerDto> exclusivePolls = fetchExclusivePollsFor(userDto);

            pll.deleteByOrganizerId(organizerId);

            exclusivePolls.forEach(this::deleteExclusivePoll);

            ul.deleteById(userDto);
            refreshListOrganizerDto();

            deleteSuccess = true;
            deleteFailure = false;
        } catch (Exception e) {
            handleError("An error occurred!", "Unexpected error: " + e.getMessage());
        }
    }

    private int getOrganizerIdFromRequest() {
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            return Integer.parseInt(params.get("organizerId"));
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    private List<PollOwnerDto> fetchExclusivePollsFor(UserDto userDto) {
        return pol.getAllByOrganizerAndIsPrimaryOrganizer(userDto, Boolean.TRUE).stream()
            .filter(pollOwner -> pol.findAllByPollId(pollOwner.getPollId()).size() == 1)
            .collect(Collectors.toList());
    }

    private void deleteExclusivePoll(PollOwnerDto pollOwner) {
        PollDto pollDto = pl.getPollById(pollOwner.getPollId());
        ppl.deleteByPoll(pollDto);
        pl.deletePollAdmin(pollDto.getId(), pollDto.getState().toString());
    }

    private void handleError(String userMessage, String logMessage) {
        deleteSuccess = false;
        deleteFailure = true;
        System.err.println(logMessage);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, userMessage, logMessage));
        deleteFailureMessage = logMessage;
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

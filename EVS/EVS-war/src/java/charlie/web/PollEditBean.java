package charlie.web;

import charlie.dto.PollDto;
import charlie.dto.UserDto;
import charlie.entity.PollStateEnum;
import charlie.logic.PollLogic;
import charlie.logic.PollParticipantLogic;
import charlie.logic.UserLogic;
import charlie.utils.DateUtils;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;

@Named(value = "pollEditBean")
@ViewScoped
public class PollEditBean implements Serializable {

    @EJB
    private PollLogic pl;

    @EJB
    private UserLogic ul;


    @EJB
    private PollParticipantLogic ppl;
    

    private PollDto pollDto;
    private boolean success;
    private boolean failure;
    private String failureMessage;

    private String pollUuid;
    
    @PostConstruct
    public void init() {
        getPollUuid();
        getPollDto();
    }

    public String getPollUuid() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        pollUuid = params.get("pollUuid");
        return pollUuid;
    }

    public void setPollUuid(String pollUuid) {
        this.pollUuid = pollUuid;
    }

    public List<PollStateEnum> getAvailableStates() {
        return Arrays.asList(PollStateEnum.values());
    }

    public String saveChanges() {
        try {
            pl.updatePoll(pollDto);
            success = true;
            failure = false;
            return "managePollPage";  // Redirect or outcome for a successful save
        } catch (EJBException e) {
            success = false;
            failure = true;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            failureMessage = t.getMessage();
            return "failure";  // Redirect or outcome for a failed save
        }
    }

    public boolean isValid() {
        return pollDto != null;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return failure;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public PollDto getPollDto() {
        if (pollDto == null) {
            pollDto = pl.getPollForEdit(pollUuid);
            pollDto.setStartsAt(DateUtils.convertDateToCustomFormat(pollDto.getStartsAt()));
            pollDto.setEndsAt(DateUtils.convertDateToCustomFormat(pollDto.getEndsAt()));
        }
        return pollDto;
    }

    public void setPollDto(PollDto pollDto) {
        this.pollDto = pollDto;
    }

}

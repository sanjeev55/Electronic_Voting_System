package charlie.web;

import charlie.dto.*;
import charlie.entity.PollStateEnum;
import charlie.logic.*;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "viewResultBean")
@ViewScoped
public class ViewResultBean implements Serializable {


    @EJB
    private PollLogic pl;

    @EJB
    private PollOwnerLogic pol;

    @EJB
    private UserLogic ul;

    @EJB
    private PollParticipantLogic ppl;

    private UserDto currentUser;
    private List<PollDto> polls;
    private boolean success;
    private boolean failure;
    private String failureMessage;

    @PostConstruct
    public void init() {
        loadCurrentUser();
    }

    private void loadCurrentUser() {
        currentUser = ul.getCurrentUser();
    }

    public UserDto getCurrentUser() {
        return currentUser;
    }

    public List<PollDto> getPolls() {
        if (polls == null) {
            List<PollOwnerDto> pollOwnerDto = pol.findAllByOrganizerAndPollState(currentUser, PollStateEnum.FINISHED);
            polls = pollOwnerDto.stream()
                                .map(PollOwnerDto::getPollId)
                                .map(pl::getPollById)
                                .collect(Collectors.toList());
        }
        return polls;
    }

    public boolean hasVotedParticipants(PollDto poll) {
        return countOfHasVoted(poll) >= 3;
    }

    public Long countOfHasVoted(PollDto poll) {
        return ppl.getCountOfPollPaticipantByPollIdAndStatus(poll, Boolean.TRUE);
    }

    public void publishPollResult(int pollId) {
        try {
            pl.publishPollResult(pollId);
            success = true;
            failure = false;
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        failure = true;
        success = false;
        Throwable rootCause = e;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        failureMessage = rootCause.getMessage();
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
}

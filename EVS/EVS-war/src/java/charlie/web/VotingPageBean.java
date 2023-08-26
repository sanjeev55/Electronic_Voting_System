package charlie.web;

import charlie.dto.PollParticipantDto;
import charlie.dto.*;
import charlie.logic.PollLogic;
import charlie.logic.PollParticipantLogic;
import charlie.utils.StringUtils;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "votingPageBean")
@ViewScoped
public class VotingPageBean implements Serializable {

    private String token;
    private boolean renderInvalidTokenMessage;
    private String errorMessage;

    private PollParticipantDto currentPollParticipant;
    private List<PollQuestionAnswerDto> pollQuestions;
    
    @EJB
    private PollParticipantLogic participantLogic;
    @EJB
    private PollLogic pollLogic;

    public void init() {
        System.out.println(token);
        if (!StringUtils.hasText(token)) {
            renderInvalidTokenMessage = true;
            errorMessage = "Invalid token!";
            return;
        }

        this.currentPollParticipant = participantLogic.getPollParticipantByToken(token);
        if (currentPollParticipant == null || currentPollParticipant.getPoll() == null) {
            renderInvalidTokenMessage = true;
            errorMessage = "Invalid token!";
            return;
        }

        if (currentPollParticipant.getHasParticipated() == Boolean.TRUE) {
            renderInvalidTokenMessage = true;
            errorMessage = "Token already used for voting!";
            this.currentPollParticipant = null;
            return;
        }
        
        // TODO: poll date validation       
    }

    public List<PollQuestionAnswerDto> getPollQuestions() {
        
        if(pollQuestions == null) {            
            pollQuestions = pollLogic.getPollQuestionsByPollId(currentPollParticipant.getPoll().getId());
        }
        System.out.println("pollQuestions: " + pollQuestions);
        return pollQuestions;
    }
   
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRenderInvalidTokenMessage() {
        return renderInvalidTokenMessage;
    }

    public void setRenderInvalidTokenMessage(boolean renderInvalidTokenMessage) {
        this.renderInvalidTokenMessage = renderInvalidTokenMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PollParticipantDto getCurrentPollParticipant() {
        return currentPollParticipant;
    }   
}

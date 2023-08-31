package charlie.web;

import charlie.dto.PollParticipantDto;
import charlie.dto.*;
import charlie.entity.PollStateEnum;
import charlie.entity.QuestionTypeEnum;
import charlie.logic.ParticipantQuestionAnswerLogic;
import charlie.logic.PollLogic;
import charlie.logic.PollParticipantLogic;
import charlie.utils.StringUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "votingPageBean")
@ViewScoped
public class VotingPageBean implements Serializable {

    private String token;
    private boolean renderInvalidTokenMessage;
    private boolean renderTokenInputForm;
    private String errorMessage;

    private PollParticipantDto currentPollParticipant;
    private List<PollQuestionAnswerDto> pollQuestions;

    @EJB
    private PollParticipantLogic participantLogic;
    @EJB
    private PollLogic pollLogic;
    @EJB
    private ParticipantQuestionAnswerLogic participantQuestionAnswerLogic;

    public void init() {
        System.out.println(token);
        if(isRenderTokenInputForm()) {
            renderInvalidTokenMessage = false;
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

        if (!currentPollParticipant.getPoll().getState().equals(PollStateEnum.VOTING)) {
            renderInvalidTokenMessage = true;
            errorMessage = "Current poll state " + currentPollParticipant.getPoll().getState() + " not allowed";
            this.currentPollParticipant = null;
            return;
        }
    }

    public List<PollQuestionAnswerDto> getPollQuestions() {

        if (pollQuestions == null) {
            pollQuestions = pollLogic.getPollQuestionsByPollId(currentPollParticipant.getPoll().getId());
        }
        System.out.println("pollQuestions: " + pollQuestions);
        return pollQuestions;
    }

    public void submitToken() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("voting-page.xhtml?token=" + token);
    }
    
    public void submitVote() throws IOException {
        System.out.println("submitting vote");

        boolean isValidationError = false;
        String validationErrorMsg = null;
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String param = parameterMap.get("jsonResponse");

        var userResponseJsonMap = StringUtils.convertJsonStringToMap(param);
        System.out.println("user resp json map: " + userResponseJsonMap);

        List<ParticipantQuestionAnswerDto> participantQuestionAnswers = new ArrayList<>();
        for (PollQuestionAnswerDto pollQuestionAnswer : pollQuestions) {
            ParticipantQuestionAnswerDto participantQuestionAnswerDto = new ParticipantQuestionAnswerDto();
            List<Integer> answers = new ArrayList<>();

            if (pollQuestionAnswer.getQuestionType().equals(QuestionTypeEnum.MULTIPLE_CHOICE.name())) {
                for (QuestionAnswerChoiceDto answerChoice : pollQuestionAnswer.getAnswerChoices()) {
                    String key = pollQuestionAnswer.getUuid() + ";" + answerChoice.getUuid();
                    if (userResponseJsonMap.containsKey(key)) {
                        answers.add(answerChoice.getId());
                        answerChoice.setIsSelected(true);
                    } else {
                        answerChoice.setIsSelected(false);
                    }
                }

                if (answers.size() < pollQuestionAnswer.getMultipleChoiceMin()) {
                    isValidationError = true;
                    validationErrorMsg = "Please select at least " + pollQuestionAnswer.getMultipleChoiceMin() + " choice for " + pollQuestionAnswer.getTitle();
                }

                if (answers.size() > pollQuestionAnswer.getMultipleChoiceMax()) {
                    isValidationError = true;
                    validationErrorMsg = "Please select at most " + pollQuestionAnswer.getMultipleChoiceMax() + " choice for " + pollQuestionAnswer.getTitle();
                }

            } else {
                Integer id = userResponseJsonMap.get(pollQuestionAnswer.getUuid());
                for (QuestionAnswerChoiceDto answerChoice : pollQuestionAnswer.getAnswerChoices()) {
                    if (answerChoice.getId() == id) {
                        answerChoice.setIsSelected(true);
                    } else {
                        answerChoice.setIsSelected(false);
                    }
                }
                answers.add(id);
            }

            participantQuestionAnswerDto.setToken(token);
            participantQuestionAnswerDto.setPollId(currentPollParticipant.getPoll().getId());
            participantQuestionAnswerDto.setAnswerChoiceIds(answers);
            participantQuestionAnswerDto.setQuestionId(pollQuestionAnswer.getId());
            participantQuestionAnswers.add(participantQuestionAnswerDto);
        }
        
        if (isValidationError) {
            renderInvalidTokenMessage = true;
            errorMessage = validationErrorMsg;
            return;
        } 
        
        System.out.println("user answer map: " + participantQuestionAnswers);
        var result = participantQuestionAnswerLogic.saveParticipantQuestionAnswer(participantQuestionAnswers);
        if (result.isError()) {
            renderInvalidTokenMessage = true;
            errorMessage = result.getError();
            return;
        }
        
        renderInvalidTokenMessage = false;
        FacesContext.getCurrentInstance().getExternalContext().redirect("thankyou-page.xhtml");
    }
    
    public void cancelVote() throws IOException {
        System.out.println("cancelling vote: " + token);
        participantLogic.cancelVoting(token);
        FacesContext.getCurrentInstance().getExternalContext().redirect("thankyou-page.xhtml");
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

    public boolean isRenderTokenInputForm() {
        return !StringUtils.hasText(token);
    }   
}

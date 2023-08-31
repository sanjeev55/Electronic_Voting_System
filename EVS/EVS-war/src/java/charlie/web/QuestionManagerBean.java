package charlie.web;

import charlie.domain.Page;
import charlie.domain.Result;
import charlie.dto.AnswerDto;
import charlie.dto.PollDto;
import charlie.dto.QuestionDto;
import charlie.entity.QuestionTypeEnum;
import charlie.logic.PollLogic;
import charlie.logic.QuestionAnswerChoiceLogic;
import charlie.logic.QuestionLogic;
import charlie.utils.StringUtils;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;

@Named(value = "questionManagerBean")
@ViewScoped
public class QuestionManagerBean implements Serializable {

    private static final Logger logger = Logger.getLogger(QuestionManagerBean.class.getName());

    @EJB
    private QuestionLogic questionService;
    @EJB
    private QuestionAnswerChoiceLogic qacl;
    @EJB
    private PollLogic pl;

    private QuestionDto questionInfo = new QuestionDto();
    private int questionId;
    private List<AnswerDto> dynamicAnswers;
    private PollDto pollDto;
    private String questionUuid;
    private List<QuestionDto> questionDto;
    private Boolean success;
    private Boolean failure;
    private String failureMessage;

    @PostConstruct
    public void init() {
        getPollDto();
        dynamicAnswers = new ArrayList<>();
    }

    public void onQuestionTypeChanged() {
        dynamicAnswers.clear();
        if (questionInfo.getType() == QuestionTypeEnum.YES_NO) {
            addYesNoAnswers();
        }
    }

    private void addYesNoAnswers() {
        AnswerDto yesAnswer = new AnswerDto();
        yesAnswer.setShortName("Yes");
        yesAnswer.setDescription("Yes");
        dynamicAnswers.add(yesAnswer);
        AnswerDto noAnswer = new AnswerDto();
        noAnswer.setShortName("No");
        noAnswer.setDescription("No");
        dynamicAnswers.add(noAnswer);
        questionInfo.setMultipleChoiceMax(1);
        questionInfo.setMultipleChoiceMin(1);
    }

    public void addDynamicAnswer() {
        dynamicAnswers.add(new AnswerDto());
        System.out.println("Dynamice answer add:" + dynamicAnswers);
    }

    public void removeDynamicAnswer(AnswerDto answer) {
        dynamicAnswers.remove(answer);
    }

    public List<QuestionTypeEnum> getQuestionTypes() {
        return Arrays.asList(QuestionTypeEnum.values());
    }

    public String addQuestion() {
        try {
            if (isTitleUnique(questionInfo.getTitle())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Question title already in use: " + questionInfo.getTitle(), null));
            }
            if(questionInfo.getType().equals(QuestionTypeEnum.SINGLE_CHOICE)){
                questionInfo.setMultipleChoiceMax(1);
                questionInfo.setMultipleChoiceMin(1);
            }
                questionInfo.setPollId(pollDto.getId());
                questionUuid = UUID.randomUUID().toString();
                questionInfo.setUuid(questionUuid);
                questionInfo.setName(questionInfo.getTitle());
                questionId = questionService.addNewQuestion(questionInfo);
                success = true;
                failure = false;
                
                return "addQuestionPage?faces-redirect=true";
            
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

    public void addQuestionAnswer() {
        try{
            addQuestion();
            if (questionId > 0) {
                addAnswers();    
            }
            resetFields();
        } catch(EJBException e){
            resetFields();
            System.out.println("Error:" + e);
            
        }
    }

    private void resetFields() {
        questionInfo = new QuestionDto();
        dynamicAnswers = new ArrayList<>();
    }

    private void addAnswers() {
        for (AnswerDto answer : dynamicAnswers) {
            System.out.println("Short name:" + answer.getShortName());
            answer.setDescription(answer.getShortName());
            answer.setQuestionId(questionId);
            answer.setUuid(UUID.randomUUID().toString());
            qacl.addAnswer(answer);
        }
    }

    public PollDto getPollDto() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int pollId = Integer.parseInt(params.get("pollId"));
        pollDto = pl.getPollById(pollId);
        return pollDto;
    }

    public QuestionDto getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(QuestionDto questionInfo) {
        this.questionInfo = questionInfo;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<AnswerDto> getDynamicAnswers() {
        return dynamicAnswers;
    }

    public void setDynamicAnswers(List<AnswerDto> dynamicAnswers) {
        this.dynamicAnswers = dynamicAnswers;
    }
    
    private boolean isTitleUnique(String name) {
        for (QuestionDto list : getQuestionDto()) {
            if (list.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public List<QuestionDto> getQuestionDto() {
        questionDto = questionService.getQuestionByPoll(pollDto);
        return questionDto;
    }

    public void setQuestionDto(List<QuestionDto> questionDto) {
        this.questionDto = questionDto;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getFailure() {
        return failure;
    }

    public void setFailure(Boolean failure) {
        this.failure = failure;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
    

    

}

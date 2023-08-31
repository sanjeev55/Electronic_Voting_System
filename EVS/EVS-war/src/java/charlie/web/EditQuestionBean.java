/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import charlie.dto.AnswerDto;
import charlie.dto.PollDto;
import charlie.dto.QuestionDto;
import charlie.entity.QuestionTypeEnum;
import charlie.logic.PollLogic;
import charlie.logic.QuestionAnswerChoiceLogic;
import charlie.logic.QuestionLogic;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "editQuestionBean")
@ViewScoped
public class EditQuestionBean implements Serializable {

    @EJB
    QuestionLogic ql;
    
    @EJB
    QuestionAnswerChoiceLogic qacl;
    
    @EJB
    PollLogic pl;
    
    private QuestionDto question;
    private List<AnswerDto> dynamicAnswers;
    private boolean success;
    private boolean failure;
    private String failureMessage;
    
    @PostConstruct
    public void init(){
        getQuestion();
        getDynamicAnswers();
    }

    public QuestionDto getQuestion() {
        if (question == null){
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int questionId = Integer.parseInt(params.get("questionId"));
            question = ql.getQuestionById(questionId);
        }
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
    }

    public List<AnswerDto> getDynamicAnswers() {
        if(dynamicAnswers ==null){
            dynamicAnswers = qacl.getByQuestion(question);
        }
        return dynamicAnswers;
       
    }

    public void setDynamicAnswers(List<AnswerDto> dynamicAnswers) {
        this.dynamicAnswers = dynamicAnswers;
    }
    
//    public void onQuestionTypeChanged() {
//        if (question.getType() == QuestionTypeEnum.YES_NO) {
//            addYesNoAnswers();
//        }
//    }

    private void addYesNoAnswers() {
        dynamicAnswers.clear();
        AnswerDto yesAnswer = new AnswerDto();
        yesAnswer.setShortName("Yes");
        yesAnswer.setDescription("Yes");
        dynamicAnswers.add(yesAnswer);
        AnswerDto noAnswer = new AnswerDto();
        noAnswer.setShortName("No");
        noAnswer.setDescription("No");
        dynamicAnswers.add(noAnswer);
        question.setMultipleChoiceMax(1);
        question.setMultipleChoiceMin(1);
    }

    public void addDynamicAnswer() {
        System.out.println("Add dynamic answers triggered");
        dynamicAnswers.add(new AnswerDto());
    }

    public void removeDynamicAnswer(AnswerDto answer) {
        System.out.println("Remove dynamic answers triggered:" + answer);
        dynamicAnswers.remove(answer);
    }

    public List<QuestionTypeEnum> getQuestionTypes() {
        return Arrays.asList(QuestionTypeEnum.values());
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String updateQuestionAnswer(){
        try{
            if (isTitleUnique(question.getTitle())) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Question title already in use: " + question.getTitle(), null));
            }
            if(question.getType().equals(QuestionTypeEnum.YES_NO)){
                addYesNoAnswers();
            }
            System.out.println("Question:" + question);
            System.out.println("Answers:" + dynamicAnswers);
            ql.updateQuestionAnswer(question, dynamicAnswers);
            success = true;
            failure= false;
            return "manageQuestionPage";
        }catch(ValidatorException e){
            success=false;
            failure=true;
            failureMessage=e.getMessage();
            return "editQuestionPage?faces-redirect=true";
        }
        
    }
    
    private boolean isTitleUnique(String name) {
        for (QuestionDto list : getQuestionList()) {
            if (list.getName().equals(name)) {
                return true;
            }
        }
        return false;
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
    
    public List<QuestionDto> getQuestionList(){
        PollDto poll = pl.getPollById(question.getPollId());
        List<QuestionDto> questionList = ql.getQuestionByPoll(poll);
        questionList.remove(question);
        return questionList;
    }
    
    
    
    
    
}

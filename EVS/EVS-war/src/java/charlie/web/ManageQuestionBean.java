/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import charlie.dto.AnswerDto;
import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.QuestionAnswerChoiceDto;
import charlie.dto.QuestionDto;
import charlie.dto.UserDto;
import charlie.entity.PollStateEnum;
import charlie.logic.PollLogic;
import charlie.logic.PollOwnerLogic;
import charlie.logic.QuestionAnswerChoiceLogic;
import charlie.logic.QuestionLogic;
import charlie.logic.UserLogic;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "manageQuestionBean")
@ViewScoped
public class ManageQuestionBean implements Serializable {

    @EJB
    private QuestionLogic ql;
    
    @EJB
    private QuestionAnswerChoiceLogic qacl;
    
    @EJB
    private PollLogic pl;
    
    @EJB
    private PollOwnerLogic pol;
    
    @EJB
    private UserLogic ul;
    
    
    private List<QuestionDto> questions = new ArrayList<>();
    private List<PollDto> polls = new ArrayList<>();    
    private List<PollOwnerDto> pollOwners;
    private UserDto currentUser;
    private Boolean success;
    private Boolean failure;
    private String failureMessage;
    
    @PostConstruct
    public void init(){
        getCurrentUser();
        getPolls();
        getQuestions();
    }

    public List<QuestionDto> getQuestions() {
//        if(questions == null){
//            for(PollDto poll: polls){
//                List<QuestionDto> questionDto = ql.getQuestionByPoll(poll);
//                questions.addAll(questionDto);
//            }
//        }

        questions = ql.getQuestionsByOrganizer(currentUser);
        System.out.println("Questions" + questions);
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public List<PollDto> getPolls() {
        List<Integer> pollIds = pol.getAllByOrganizer(currentUser)
                .stream()
                .map(poll -> poll.getPollId())
                .collect(Collectors.toList());
        polls = pollIds
                .stream()
                .map(poll -> pl.getPollById(poll))
                .collect(Collectors.toList());
        return polls;
    }

    public void setPolls(List<PollDto> polls) {
        this.polls = polls;
    }

    public UserDto getCurrentUser() {
        currentUser = ul.getCurrentUser();
        return currentUser;
    }

    public void setCurrentUser(UserDto currentUser) {
        this.currentUser = currentUser;
    }

    public List<PollOwnerDto> getPollOwners() {
        pollOwners = pol.getAllByOrganizer(currentUser);
        return pollOwners;
    }

    public void setPollOwners(List<PollOwnerDto> pollOwners) {
        this.pollOwners = pollOwners;
    }

    
    public int getAnswersCount(int questionId){
        List<QuestionAnswerChoiceDto> answers = qacl.getAllByQuestionId(questionId);
        return answers.size();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteQuestion(QuestionDto questionDto){
        try{
            ql.deleteQuestionAnswer(questionDto);
            success=true;
            failure=false;
            
            questions = new ArrayList<>();
        } catch(Exception e){
            success=false;
            failure=true;
            
            failureMessage = e.getMessage();
        }
    }
    
    public PollStateEnum getPollState(int questionId){
        int pollId = ql.getQuestionById(questionId).getPollId();
        PollStateEnum state = pl.getPollById(pollId).getState();
        return state;
    }
    
    public String getPollName(int pollId){
        return pl.getPollById(pollId).getTitle();
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import charlie.dto.ParticipantQuestionAnswerDto;
import charlie.dto.PointDto;
import charlie.dto.PollDto;
import charlie.dto.QuestionAnswerChoiceDto;
import charlie.dto.VotingResultDto;
import charlie.logic.ParticipantQuestionAnswerLogic;
import charlie.logic.PollLogic;
import charlie.logic.PollParticipantLogic;
import charlie.logic.QuestionAnswerChoiceLogic;
import charlie.logic.QuestionLogic;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.logging.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "viewPollResultBean")
@ViewScoped
public class ViewPollResultBean implements Serializable {

    @EJB
    private ParticipantQuestionAnswerLogic pqal;
    @EJB
    private PollLogic pl;
    @EJB
    private QuestionLogic ql;
    @EJB
    private QuestionAnswerChoiceLogic qacl;
    @EJB
    private PollParticipantLogic ppl;
    
    private List<ParticipantQuestionAnswerDto> pqad;
    private PollDto pollDto;
    private List<VotingResultDto> votingResults;
    
    private static final Logger logger = Logger.getLogger(ViewPollResultBean.class.getName());

    @PostConstruct
    public void init() {
        loadPollDto();
        loadPqad();
        computeVotingResults();
    }

    public List<ParticipantQuestionAnswerDto> getPqad() {
        return pqad;
    }

    public void setPqad(List<ParticipantQuestionAnswerDto> pqad) {
        this.pqad = pqad;
    }

    public PollDto getPollDto() {
        return pollDto;
    }

    public void setPollDto(PollDto pollDto) {
        this.pollDto = pollDto;
    }

    public List<VotingResultDto> getVotingResults() {
        return votingResults;
    }

    public void setVotingResults(List<VotingResultDto> votingResults) {
        this.votingResults = votingResults;
    }

    private void loadPollDto() {
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String pollUuid = params.get("pollUuid");
            pollDto = pl.getPollByUuid(pollUuid);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Error parsing pollId", e);
        }
    }

    private void loadPqad() {
        if (pollDto != null) {
            pqad = pqal.getAllByPoll(pollDto);
            logger.log(Level.INFO, "Loaded pqad: {0}", pqad);
        } else {
            logger.warning("PollDto not initialized. Skipping loading pqad.");
        }
    }
    
    private void computeVotingResults() {
        votingResults = new ArrayList<>();

        Map<Integer, Map<String, Integer>> resultMap = initializeResultMap();

        for (ParticipantQuestionAnswerDto answerDto : pqad) {
            processAnswerChoices(answerDto, resultMap);
        }

        convertToVotingResults(resultMap);
        
    }

    private Map<Integer, Map<String, Integer>> initializeResultMap() {
        Map<Integer, Map<String, Integer>> resultMap = new HashMap<>();
        for (ParticipantQuestionAnswerDto answerDto : pqad) {
            if (!resultMap.containsKey(answerDto.getQuestionId())) {
                Map<String, Integer> choiceCounts = new HashMap<>();
                List<QuestionAnswerChoiceDto> answerChoices = qacl.getAllByQuestionId(answerDto.getQuestionId());
                for (QuestionAnswerChoiceDto choice : answerChoices) {
                    choiceCounts.put(choice.getShortName(), 0);
                }
                resultMap.put(answerDto.getQuestionId(), choiceCounts);
            }
        }
        return resultMap;
    }

    private void processAnswerChoices(ParticipantQuestionAnswerDto answerDto, Map<Integer, Map<String, Integer>> resultMap) {
        for (Integer choiceId : answerDto.getAnswerChoiceIds()) {
            String answerChoice = qacl.getById(choiceId).getShortName();
            resultMap.get(answerDto.getQuestionId()).put(answerChoice, resultMap.get(answerDto.getQuestionId()).getOrDefault(answerChoice, 0) + 1);
        }
    }

    private void convertToVotingResults(Map<Integer, Map<String, Integer>> resultMap) {
        for (Map.Entry<Integer, Map<String, Integer>> entry : resultMap.entrySet()) {
            Integer questionId = entry.getKey();
            String questionTitle = ql.getQuestionById(questionId).getTitle();
            VotingResultDto votingResult = new VotingResultDto();
            votingResult.setQuestionTitle(questionTitle);
            votingResult.setAnswerChoiceCounts(entry.getValue());
            votingResults.add(votingResult);
            logger.log(Level.INFO, "Voting result:{0} answer count {1}", new Object[]{votingResult.getQuestionTitle(), votingResult.getAnswerChoiceCounts()});
        }
        
    }
    
    public List<PointDto> convertToPoints(VotingResultDto result) {
        List<PointDto> points = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : result.getAnswerChoiceCounts().entrySet()) {
            points.add(new PointDto(entry.getKey(),entry.getValue()));
            
        }
        System.out.println("Points" + points);
        return points;
    }
    
    public Long hasVotedParticipants(){
        return ppl.getCountOfPollPaticipantByPollIdAndStatus(pollDto, Boolean.TRUE);
    }
    
    public Long hasNotParticiated(){
        return ppl.getCountOfPollPaticipantByPollIdAndStatus(pollDto, Boolean.FALSE);
    }
    
    
    
}

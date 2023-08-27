package charlie.logic.impl;

import charlie.dao.ParticipantQuestionAnswerAccess;
import charlie.dao.PollAccess;
import charlie.dao.PollParticipantAccess;
import charlie.domain.ParticipantResponse;
import charlie.domain.Result;
import charlie.dto.ParticipantQuestionAnswerDto;
import charlie.entity.PollStateEnum;
import charlie.logic.ParticipantQuestionAnswerLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

@Stateless
public class ParticipantQuestionAnswerLogicImpl implements ParticipantQuestionAnswerLogic{

    private static final Logger LOG = Logger.getLogger(ParticipantQuestionAnswerLogicImpl.class.getName());
    
    @EJB
    private ParticipantQuestionAnswerAccess pqa;
    
    @EJB
    private PollAccess pa;
    
    @EJB
    private PollParticipantAccess ppa;
    
    @Override
    @Transactional
    public Result<String> saveParticipantQuestionAnswer(List<ParticipantQuestionAnswerDto> request) {
        if(request == null || request.isEmpty())
            return Result.error("Cannot save empty request");
        
        String token = request.get(0).getToken();
        Integer pollId = request.get(0).getPollId();
        
        var pollParticipantInfo = ppa.findByToken(token);
        if(pollParticipantInfo == null || pollParticipantInfo.getHasParticipated() == Boolean.TRUE)
            return Result.error("Token is either previously used or invalid");
        
        var pollInfo = pa.getPollById(pollId);
        if(pollInfo == null || !pollInfo.getState().equals(PollStateEnum.VOTING))
            return Result.error("Voting period for the poll is finished");
        
        List<ParticipantResponse> participantResponses = new ArrayList<>();
        for(ParticipantQuestionAnswerDto dto : request) {
            var validAnswerChoices = dto.getAnswerChoiceIds().stream().filter(answer -> answer != null).collect(Collectors.toList());
            for(Integer answerId : validAnswerChoices) {
                ParticipantResponse participantResponse = new ParticipantResponse();
                participantResponse.setQuestionId(dto.getQuestionId());
                participantResponse.setAnswerId(answerId);
                participantResponse.setPollId(dto.getPollId());
                participantResponses.add(participantResponse);
            }
        }
        
        pqa.saveParticipantResponses(participantResponses);
        pollParticipantInfo.setHasParticipated(Boolean.TRUE);
        ppa.edit(pollParticipantInfo);
        return Result.ok("Saved successfully");
    }
    
}

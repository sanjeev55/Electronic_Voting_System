/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.PollParticipantAccess;
import charlie.dto.ParticipantDto;
import charlie.dto.PollDto;
import charlie.dto.PollParticipantDto;
import charlie.entity.PollParticipantEntity;
import charlie.logic.PollParticipantLogic;
import charlie.mapper.PollParticipantMapper;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class PollParticipantLogicImpl implements PollParticipantLogic {
    
    @EJB
    PollParticipantAccess ppa;
    
    @EJB
    PollParticipantMapper ppm;

    @Override
    public PollParticipantDto getParticipantsOfPoll(PollDto poll) {
        
        return null;
    }

    @Override
    public void savePollParticipant(PollParticipantDto pollParticipantDto) {
        ppa.create(ppm.toEntity(pollParticipantDto));
    }

    @Override
    public void savePollParticipantList(PollDto poll, List<ParticipantDto> participantList) {
        
    }
    
    @Override
    public PollParticipantDto findByPollIdAndParticipantId(int pollId, int participantId){
        PollParticipantEntity ppe = ppa.findByPollIdAndParticipantId(pollId, participantId);
        return ppm.toDto(ppe);
    }
    
}

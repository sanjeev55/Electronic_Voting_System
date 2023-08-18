/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.ParticipantAccess;
import charlie.dto.ParticipantDto;
import charlie.entity.ParticipantEntity;
import charlie.logic.ParticipantLogic;
import charlie.mapper.ParticipantEntityMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class ParticipantLogicImpl implements ParticipantLogic{
    
    @EJB
    private ParticipantAccess pa;
    
    @EJB
    private ParticipantEntityMapper pem;

    @Override
    public ParticipantDto getById(int participantId) {
        ParticipantEntity pe = pa.find(participantId);
        return pem.toDto(pe);
    }

    @Override
    public List<ParticipantDto> getAll() {
        List<ParticipantEntity> peList = pa.findAll();
        return peList.stream().map(pem::toDto).collect(Collectors.toList());
    }
    
    @Override
    public List<ParticipantDto> getParticipantByPollId(int pollId){
        List<ParticipantEntity> peList = pa.findParticipantsByPoll(pollId);
        return peList.stream().map(pem::toDto).collect(Collectors.toList());
    }

    @Override
    public ParticipantDto getParticipantByEmail(String email) {
        ParticipantEntity pe = pa.findParticipantByEmail(email);
        return pem.toDto(pe);
    }
    
    @Override
    public void createParticipant(ParticipantDto participantDto){
        ParticipantEntity pe = pem.toEntity(participantDto);
        pa.create(pe);
    }
    
}

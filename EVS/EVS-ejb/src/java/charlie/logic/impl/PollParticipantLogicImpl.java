/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.PollParticipantAccess;
import charlie.dto.PollDto;
import charlie.dto.PollParticipantDto;
import charlie.entity.PollEntity;
import charlie.entity.PollParticipantEntity;
import charlie.logic.PollParticipantLogic;
import charlie.mapper.PollEntityMapper;
import charlie.mapper.PollParticipantMapper;
import java.util.List;
import java.util.stream.Collectors;
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
    
    @EJB
    PollEntityMapper pm;

    @Override
    public List<PollParticipantDto> getParticipantsOfPoll(PollDto pollDto) {
        PollEntity pe = pm.toEntity(pollDto);
        List<PollParticipantEntity> ppe = ppa.findAllByPoll(pe);
        return ppe.stream().map(ppm::toDto).collect(Collectors.toList());
    }

    @Override
    public void savePollParticipant(PollParticipantDto pollParticipantDto) {
        ppa.create(ppm.toEntity(pollParticipantDto));
    }
    
}

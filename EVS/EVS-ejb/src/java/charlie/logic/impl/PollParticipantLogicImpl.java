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

    @Override
    public PollParticipantDto getByToken(String token) {
        PollParticipantEntity ppe = ppa.findByToken(token);
        return ppm.toDto(ppe);
    }

    @Override
    public List<PollParticipantDto> getParticipantsByParticipationStatus(Boolean hasParticipated) {
        List<PollParticipantEntity> ppe = ppa.findParticipantsByParticipationStatus(hasParticipated);
        return ppe.stream().map(ppm::toDto).collect(Collectors.toList());
    }

    @Override
    public PollParticipantDto getByEmail(String email) {
        PollParticipantEntity ppe = ppa.findByEmail(email);
        return ppm.toDto(ppe);
    }

    @Override
    public Long getCountOfParticipantInPoll(PollDto pollDto) {
        return ppa.countPollParticipantByPoll(pm.toEntity(pollDto));
    }

    @Override
    public PollParticipantDto getById(int id) {
        PollParticipantEntity ppe = ppa.find(id);
        return ppm.toDto(ppe);
    }
    
    @Override
    public void deleteByPoll(PollDto pollDto){
        ppa.deleteByPollId(pm.toEntity(pollDto));
    }
    
    @Override
    public PollParticipantDto getPollParticipantByToken(String token) {        
       var entity = ppa.findByToken(token);
       if(entity == null)
           return null;
       
       return ppm.toDto(entity);
    }
    
    @Override
    public Long getCountOfPollPaticipantByPollIdAndStatus(PollDto pollDto, Boolean hasParticipated){
        return ppa.countPollPaticipantByPollIdAndStatus(pm.toEntity(pollDto), hasParticipated);
    }

    @Override
    public void cancelVoting(String token) {
        PollParticipantEntity entity = ppa.findByToken(token);
        entity.setHasParticipated(Boolean.TRUE);
        ppa.edit(entity);
    }
}

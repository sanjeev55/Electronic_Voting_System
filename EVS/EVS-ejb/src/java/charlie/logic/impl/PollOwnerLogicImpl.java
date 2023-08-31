/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.PollOwnerAccess;
import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.UserDto;
import charlie.entity.PollOwnerEntity;
import charlie.entity.PollStateEnum;
import charlie.logic.PollOwnerLogic;
import charlie.mapper.PollEntityMapper;
import charlie.mapper.PollOwnerEntityMapper;
import charlie.mapper.UserEntityMapper;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class PollOwnerLogicImpl implements PollOwnerLogic {
    @EJB
    private PollOwnerAccess poa;
    @EJB
    private PollOwnerEntityMapper pom;
    @EJB
    private UserEntityMapper um;
    @EJB
    private PollEntityMapper pm;
    
    @Override
    public List<PollOwnerDto> getAllByOrganizerAndIsPrimaryOrganizer(UserDto userDto, Boolean isPrimaryOrganizer) {
        List<PollOwnerEntity> poe = poa.getAllByOrganizerAndIsPrimaryOrganizer(um.toEntity(userDto), isPrimaryOrganizer);
        return poe.stream().map(pom::toDto).collect(Collectors.toList());
    }
    
    @Override
    public void deleteAllByOrganizer(UserDto userDto) {
        poa.deleteByOrganizer(um.toEntity(userDto));
    }

    @Override
    public void deleteById(int id) {
        poa.deleteById(id);
    }
    
    @Override
    public List<PollOwnerDto> findAllByPoll(PollDto pollDto){
        List<PollOwnerEntity> poe = poa.findAllByPoll(pm.toEntity(pollDto));
        return poe.stream().map(pom::toDto).collect(Collectors.toList());
    }
    
    @Override
    public List<PollOwnerDto> findAllByPollId(int pollId){
        List<PollOwnerEntity> poe = poa.findAllByPollId(pollId);
        return poe.stream().map(pom::toDto).collect(Collectors.toList());
    }
    
    @Override
    public void deleteByPoll(PollDto pollDto){
        poa.deleteAllByPoll(pm.toEntity(pollDto));
    }
    
    @Override
    public List<PollOwnerDto> findAllByOrganizerAndPollState(UserDto userDto, PollStateEnum state){
        List<PollOwnerEntity> poe = poa.getAllByOrganizerAndPollState(um.toEntity(userDto), state);
        return poe.stream().map(pom::toDto).collect(Collectors.toList());
    }
    
    @Override
    public PollOwnerDto getPrimaryOrganizerByPoll(PollDto pollDto, Boolean isPrimaryOrganizer){
        try{
            PollOwnerEntity pod = poa.getByPollAndIsPrimaryOraginzer(pm.toEntity(pollDto), isPrimaryOrganizer);
            return pom.toDto(pod);
        }catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public List<PollOwnerDto> getAllByOrganizer(UserDto user){
        List<PollOwnerEntity> poe = poa.getAllByOrganizer(um.toEntity(user));
        return poe.stream().map(pom::toDto).collect(Collectors.toList());
    }
    
    @Override
    public PollOwnerDto getPollByOrganizer(UserDto user, PollDto poll){
        PollOwnerEntity pod = poa.getPollByOrganizer(um.toEntity(user),pm.toEntity(poll));
        return pom.toDto(pod);
    }
    
}

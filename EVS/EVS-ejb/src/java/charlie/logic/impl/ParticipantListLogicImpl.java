/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.ParticipantListAccess;
import charlie.dto.ParticipantListDto;
import charlie.entity.ParticipantListEntity;
import charlie.logic.ParticipantListLogic;
import charlie.mapper.ParticipantListEntityMapper;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class ParticipantListLogicImpl implements ParticipantListLogic{
    
    @EJB
    ParticipantListAccess pla;
    @EJB
    ParticipantListEntityMapper plm;
    
    @Override
    public List<ParticipantListDto> getListByOriganizer(int organizerId) {
        List<ParticipantListEntity> ple = pla.findParticipantListByOrganizerId(organizerId);
        System.out.println("PLE:" + ple);
        return ple.stream()
                .map(plm::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public void saveParticipantList(ParticipantListDto participantListDto){
        participantListDto.setUuid(UUID.randomUUID().toString());
        ParticipantListEntity ple = plm.toEntity(participantListDto);
        pla.create(ple);
    }
    
    @Override
    public void deleteParticipantListById(int listId){
        ParticipantListEntity ple = pla.find(listId);
        System.out.println("PLE delete:" + ple);
        
        pla.remove(ple);
    }
    
    @Override
    public ParticipantListDto getByParticipantListById(int id){
        ParticipantListEntity ple = pla.find(id);
        return plm.toDto(ple);
    }
    
    @Override
    public void updateParticipantList(ParticipantListDto pld){
        
        pla.edit(plm.toEntity(pld));
        
    }
    
    @Override
    public void deleteByOrganizerId(int organizerId){
        pla.deleteParicipantListByPollId(organizerId);
    }
    
}

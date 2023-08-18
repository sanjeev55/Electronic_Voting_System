/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.mapper;

import charlie.dto.PollParticipantDto;
import charlie.entity.PollParticipantEntity;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class PollParticipantMapper extends AbstractEntityMapper<PollParticipantEntity, PollParticipantDto> {
    @EJB
    private ParticipantListEntityMapper participantEntityMapper;
    
    @EJB
    private PollEntityMapper pollEntityMapper; 

    @Override
    public PollParticipantEntity toEntity(PollParticipantDto dto) {
        if (dto == null) {
            return null;
        }

        super.setEntity(new PollParticipantEntity());
        PollParticipantEntity entity = super.toEntity(dto);
        entity.setToken(dto.getToken());
        entity.setHasParticipated(dto.getHasParticipated());
        entity.setEmail(dto.getEmail());


        return entity;
    }

    @Override
    public PollParticipantDto toDto(PollParticipantEntity entity) {
        if (entity == null) {
            return null;
        }

        super.setDto(new PollParticipantDto());
        PollParticipantDto dto = super.toDto(entity);
        dto.setToken(entity.getToken());
        dto.setHasParticipated(entity.getHasParticipated());
        dto.setEmail(entity.getEmail());

        return dto;
    }
}
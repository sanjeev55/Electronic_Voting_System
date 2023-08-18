/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.mapper;

import charlie.dto.ParticipantDto;
import charlie.entity.ParticipantEntity;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class ParticipantEntityMapper extends AbstractEntityMapper<ParticipantEntity, ParticipantDto> {
    @EJB
    private UserEntityMapper userEntityMapper;
    @EJB
    private PollEntityMapper pollEntityMapper;

    @Override
    public ParticipantEntity toEntity(ParticipantDto dto) {
        if (dto == null) {
            return null;
        }

        super.setEntity(new ParticipantEntity());
        ParticipantEntity entity = super.toEntity(dto);
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        if(dto.getOrganizer()!=null){
            entity.setOrganizer(userEntityMapper.toEntity(dto.getOrganizer()));
        }
        if(dto.getPoll()!=null){
            entity.setPoll(pollEntityMapper.toEntity(dto.getPoll()));
        }
        

        return entity;
    }

    @Override
    public ParticipantDto toDto(ParticipantEntity entity) {
        if (entity == null) {
            return null;
        }

        super.setDto(new ParticipantDto());
        ParticipantDto dto = super.toDto(entity);
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        if(entity.getPoll()!=null){
            dto.setPoll(pollEntityMapper.toDto(entity.getPoll()));
        }

        if (entity.getOrganizer() != null) {
            dto.setOrganizer(userEntityMapper.toDto(entity.getOrganizer()));
        }

        return dto;
    }
}

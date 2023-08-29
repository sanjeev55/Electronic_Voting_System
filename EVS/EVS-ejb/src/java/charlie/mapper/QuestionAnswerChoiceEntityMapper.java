/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.mapper;

import charlie.dto.QuestionAnswerChoiceDto;
import charlie.entity.QuestionAnswerChoiceEntity;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class QuestionAnswerChoiceEntityMapper extends AbstractEntityMapper<QuestionAnswerChoiceEntity, QuestionAnswerChoiceDto> {
    
    @Override
    public QuestionAnswerChoiceDto toDto(QuestionAnswerChoiceEntity entity) {
        if (entity == null) {
            return null;
        }

        QuestionAnswerChoiceDto dto = new QuestionAnswerChoiceDto();
        dto.setShortName(entity.getShortName());
        dto.setDescription(entity.getDescription());

        return dto;
    }
    
    @Override
    public QuestionAnswerChoiceEntity toEntity(QuestionAnswerChoiceDto dto) {
        if (dto == null) {
            return null;
        }

        QuestionAnswerChoiceEntity entity = new QuestionAnswerChoiceEntity();
        entity.setShortName(dto.getShortName());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}

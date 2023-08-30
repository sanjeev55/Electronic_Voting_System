/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.mapper;

import charlie.dto.AnswerDto;
import charlie.entity.QuestionAnswerChoiceEntity;
import charlie.logic.QuestionLogic;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class AnswerEntityMapper extends AbstractEntityMapper<QuestionAnswerChoiceEntity, AnswerDto> {
    
    @EJB
    QuestionLogic ql;
    @EJB
    QuestionEntityMapper qm;
    
    @Override
    public QuestionAnswerChoiceEntity toEntity(AnswerDto domain) {
    
        if (domain == null) {
            return null;
        }

        super.setEntity(new QuestionAnswerChoiceEntity());
        QuestionAnswerChoiceEntity entity = super.toEntity(domain);
        entity.setShortName(domain.getShortName());
        entity.setDescription(domain.getDescription());
        entity.setPollQuestion(qm.toEntity(ql.getQuestionById(domain.getQuestionId())));
        

        return entity;
    }

    @Override
    public AnswerDto toDto(QuestionAnswerChoiceEntity entity) {
        
        if (entity ==null) {
            return null;
        }

        super.setDto(new AnswerDto());
        AnswerDto dto = super.toDto(entity);
        dto.setShortName(entity.getShortName());
        dto.setDescription(entity.getDescription());
        dto.setQuestionId(entity.getPollQuestion().getId());
        

        return dto;
    }
    
}

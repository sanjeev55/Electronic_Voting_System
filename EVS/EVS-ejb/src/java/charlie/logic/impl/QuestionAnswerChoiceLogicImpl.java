/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.QuestionAnswerChoiceAccess;
import charlie.dto.QuestionAnswerChoiceDto;
import charlie.entity.QuestionAnswerChoiceEntity;
import charlie.logic.QuestionAnswerChoiceLogic;
import charlie.mapper.QuestionAnswerChoiceEntityMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class QuestionAnswerChoiceLogicImpl implements QuestionAnswerChoiceLogic {
    @EJB
    private QuestionAnswerChoiceAccess qaca;
    @EJB
    private QuestionAnswerChoiceEntityMapper qacm;
    
    @Override
    public QuestionAnswerChoiceDto getById(int id) {
        return qacm.toDto(qaca.find(id));
    }
    
    @Override
    public List<QuestionAnswerChoiceDto> getAllByQuestionId(int questionId){
        List<QuestionAnswerChoiceEntity> qace = qaca.findAllByQuestionId(questionId);
        return qace.stream().map(qacm::toDto).collect(Collectors.toList());
    }
    
}

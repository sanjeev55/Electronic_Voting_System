/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.QuestionAnswerChoiceAccess;
import charlie.dto.AnswerDto;
import charlie.dto.QuestionAnswerChoiceDto;
import charlie.dto.QuestionDto;
import charlie.entity.QuestionAnswerChoiceEntity;
import charlie.logic.QuestionAnswerChoiceLogic;
import charlie.mapper.AnswerEntityMapper;
import charlie.mapper.QuestionAnswerChoiceEntityMapper;
import java.util.List;
import java.util.UUID;
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
    @EJB
    private AnswerEntityMapper am;
    
    @Override
    public QuestionAnswerChoiceDto getById(int id) {
        return qacm.toDto(qaca.find(id));
    }
    
    @Override
    public List<QuestionAnswerChoiceDto> getAllByQuestionId(int questionId){
        List<QuestionAnswerChoiceEntity> qace = qaca.findAllByQuestionId(questionId);
        return qace.stream().map(qacm::toDto).collect(Collectors.toList());
    }
    
    @Override
    public void addAnswer(AnswerDto answerDto){
        qaca.create(am.toEntity(answerDto));
    }
    
    @Override
    public List<AnswerDto> getByQuestion(QuestionDto questionDto){
        List<QuestionAnswerChoiceEntity> qace = qaca.findAllByQuestionId(questionDto.getId());
        return qace.stream().map(am::toDto).collect(Collectors.toList());
    }
    
    @Override
    public void deleteAnswer(AnswerDto answerDto){
        qaca.remove(am.toEntity(answerDto));
    }
    
    public void updateAnswer(AnswerDto answer){
        qaca.edit(am.toEntity(answer));
    }
    
    @Override
    public void updateAnswers(List<AnswerDto> newAnswerDto, QuestionDto question){
        
        List<AnswerDto> originalAnswers = getByQuestion(question);

        for (AnswerDto answer : newAnswerDto) {
            if (answer.getId() == null) {
                // This is a new answer
                answer.setUuid(UUID.randomUUID().toString());
                answer.setDescription(answer.getShortName());
                answer.setQuestionId(question.getId());
                addAnswer(answer);
            } else {
                updateAnswer(answer);
                originalAnswers.remove(answer); // Remove it from the original list as it's still present
            }
        }

        // delete remaining answers
        for (AnswerDto answer : originalAnswers) {
            deleteAnswer(answer);
        }
    }
    
}

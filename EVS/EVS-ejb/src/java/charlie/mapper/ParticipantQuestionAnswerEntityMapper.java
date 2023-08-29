package charlie.mapper;

import charlie.dto.ParticipantQuestionAnswerDto;
import charlie.entity.ParticipantQuestionAnswerEntity;
import charlie.logic.PollLogic;
import charlie.logic.QuestionLogic;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ParticipantQuestionAnswerEntityMapper extends AbstractEntityMapper<ParticipantQuestionAnswerEntity, ParticipantQuestionAnswerDto> {
    
    @EJB
    private PollEntityMapper pollEntityMapper;
    @EJB
    private PollLogic pl;
    @EJB
    private QuestionLogic ql;
    @EJB
    private QuestionEntityMapper qm;

    @Override
    public ParticipantQuestionAnswerEntity toEntity(ParticipantQuestionAnswerDto dto) {
        if (dto == null) {
            return null;
        }

        ParticipantQuestionAnswerEntity entity = new ParticipantQuestionAnswerEntity();
        entity.setPoll(pollEntityMapper.toEntity(pl.getPollById(dto.getId())));
        entity.setQuestion(qm.toEntity(ql.getQuestionById(dto.getQuestionId())));
        return entity;
    }

    @Override
    public ParticipantQuestionAnswerDto toDto(ParticipantQuestionAnswerEntity entity) {
        if (entity == null) {
            return null;
        }

        ParticipantQuestionAnswerDto dto = new ParticipantQuestionAnswerDto();
        dto.setPollId(entity.getPoll().getId());
        dto.setQuestionId(entity.getQuestion().getId());
        dto.setAnswerChoiceIds(Arrays.asList(entity.getAnswer().getId()));

        return dto;
    }
}

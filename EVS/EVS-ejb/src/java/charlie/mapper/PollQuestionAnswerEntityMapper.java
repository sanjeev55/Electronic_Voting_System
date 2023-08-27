package charlie.mapper;

import charlie.dto.PollQuestionAnswerDto;
import charlie.dto.QuestionAnswerChoiceDto;
import charlie.entity.PollQuestionEntity;
import charlie.entity.QuestionAnswerChoiceEntity;
import charlie.entity.QuestionTypeEnum;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PollQuestionAnswerEntityMapper extends AbstractEntityMapper<PollQuestionEntity, PollQuestionAnswerDto>{
    
    @Override
    public PollQuestionAnswerDto toDto(PollQuestionEntity entity) {
        if (entity == null) {
            return null;
        }
        
        super.setDto(new PollQuestionAnswerDto());
        var dto = super.toDto(entity);
        dto.setTitle(entity.getTitle());
        dto.setQuestionType(entity.getType().name());
        dto.setDescription(entity.getDescription());
        dto.setMultipleChoiceMax(entity.getMultipleChoiceMax());
        dto.setMultipleChoiceMin(entity.getMultipleChoiceMin());
        if(entity.getAnswerChoices() == null || entity.getAnswerChoices().isEmpty()) {
            dto.setAnswerChoices(Collections.emptyList());
        } else {
            var answers = entity.getAnswerChoices().stream().map(answer -> toAnswerChoiceDto(entity, answer)).collect(Collectors.toList());
            dto.setAnswerChoices(answers);
        }
        return dto;
    }
    
    public List<PollQuestionAnswerDto> toDtoList(List<PollQuestionEntity> entities) {
        if(entities == null || entities.isEmpty())
            return Collections.emptyList();
        
        return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
    
    private QuestionAnswerChoiceDto toAnswerChoiceDto(PollQuestionEntity entity, QuestionAnswerChoiceEntity questionAnswerChoiceEntity) {
        if(questionAnswerChoiceEntity == null)
            return null;
        
        var dto = new QuestionAnswerChoiceDto();
        dto.setId(questionAnswerChoiceEntity.getId());
        dto.setUuid(questionAnswerChoiceEntity.getUuid());
        dto.setInputType(isMultiSelect(entity.getType()) ? "checkbox" : "radio");
        dto.setInputName(isMultiSelect(entity.getType()) ? entity.getUuid() + ";" + questionAnswerChoiceEntity.getUuid(): entity.getUuid());
        dto.setShortName(questionAnswerChoiceEntity.getShortName());
        dto.setDescription(questionAnswerChoiceEntity.getDescription());
        return dto;
    }
    
    private boolean isMultiSelect(QuestionTypeEnum questionType) {
        return questionType == QuestionTypeEnum.MULTIPLE_CHOICE;
    }   
}

package charlie.mapper;

import charlie.dto.QuestionDto;
import charlie.entity.PollQuestionEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

@Stateless
@LocalBean
public class QuestionEntityMapper extends AbstractEntityMapper<PollQuestionEntity,QuestionDto>{
    
    @EJB
    private UserEntityMapper userEntityMapper;

    @Override
    public PollQuestionEntity toEntity(QuestionDto domain) {
    
        if (domain == null) {
            return null;
        }

        super.setEntity(new PollQuestionEntity());
        PollQuestionEntity entity = super.toEntity(domain);
        entity.setTitle(domain.getTitle());
        entity.setType(domain.getType());
        entity.setMultipleChoiceMax(domain.getMultipleChoiceMax());
        entity.setMultipleChoiceMin(entity.getMultipleChoiceMin());
        entity.setDescription(domain.getDescription());
        entity.setName(domain.getName());

        return entity;
    }

    @Override
    public QuestionDto toDto(PollQuestionEntity entity) {
        
        if (entity ==null) {
            return null;
        }

        super.setDto(new QuestionDto());
        QuestionDto dto = super.toDto(entity);
        dto.setTitle(entity.getTitle());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setMultipleChoiceMax(entity.getMultipleChoiceMax());
        dto.setMultipleChoiceMin(entity.getMultipleChoiceMin());
        dto.setName(entity.getName());

        return dto;
    }
    
    public List<QuestionDto> toDomainList(List<PollQuestionEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();
        
        return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
}
}













package charlie.mapper;

import charlie.dto.QuestionDto;
import charlie.entity.PollQuestionEntity;
import charlie.utils.DateUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 */

@Stateless
@LocalBean
public class QuestionEntityMapper extends AbstractEntityMapper<PollQuestionEntity,QuestionDto>{
    
    @Override
    public PollQuestionEntity toEntity(QuestionDto domain) {
        
        if (domain == null) {
            return null;
        }
        
        super.setEntity(new PollQuestionEntity());
        PollQuestionEntity entity = super.toEntity(domain);
        entity.setTitle(domain.getTitle());
        entity.setType(domain.getType());
        
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
        
        return dto;
    }
    
    public List<QuestionDto> toDomainList(List<PollQuestionEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();
        
        return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }
}













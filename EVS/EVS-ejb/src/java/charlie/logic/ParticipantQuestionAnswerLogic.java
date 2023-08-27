package charlie.logic;

import charlie.domain.Result;
import charlie.dto.ParticipantQuestionAnswerDto;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface ParticipantQuestionAnswerLogic {
    Result<String> saveParticipantQuestionAnswer(List<ParticipantQuestionAnswerDto> request);
}

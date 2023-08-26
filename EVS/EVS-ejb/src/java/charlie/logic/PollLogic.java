package charlie.logic;

import charlie.domain.Page;
import charlie.domain.PollPaginationRequest;
import charlie.domain.Result;
import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.PollQuestionAnswerDto;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface PollLogic {
    PollDto getPollById(int id);
    
    PollDto getPollForEdit(String uuid);
    
    void updatePoll(PollDto pollDto);
    
    Result<PollDto> save(PollDto domain);
    
    Result<String> changePollStateToStarted(Integer id);
    
    Page<PollDto> getAllWithPagination(PollPaginationRequest request);
    
    void deleteById(int id);
    
    Result<PollDto> getPollByUUID(String uuid);
    
    List<PollOwnerDto> getPollOwners(int pollId);
    
    void deletePollOrganizerById(int id);
    
    Result<?> addOrganizerToPoll(Integer pollId, Integer organizerId);
    
    List<PollQuestionAnswerDto> getPollQuestionsByPollId(Integer pollId);
}
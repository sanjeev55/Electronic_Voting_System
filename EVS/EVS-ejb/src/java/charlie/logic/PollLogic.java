package charlie.logic;

import charlie.domain.Page;
import charlie.domain.PollPaginationRequest;
import charlie.domain.Result;
import charlie.dto.PollDto;
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
}
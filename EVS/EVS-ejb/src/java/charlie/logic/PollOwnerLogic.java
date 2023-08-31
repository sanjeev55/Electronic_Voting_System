/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic;

import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.UserDto;
import charlie.entity.PollStateEnum;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Remote
public interface PollOwnerLogic {
    public List<PollOwnerDto> getAllByOrganizerAndIsPrimaryOrganizer(UserDto userDto, Boolean isPrimaryOrganizer);
    public void deleteById(int id);
    public void deleteAllByOrganizer(UserDto userDto);
    public List<PollOwnerDto> findAllByPoll(PollDto pollDto);
    public List<PollOwnerDto> findAllByPollId(int pollId);
    public void deleteByPoll(PollDto pollDto);
    public List<PollOwnerDto> findAllByOrganizerAndPollState(UserDto user, PollStateEnum state);
    public PollOwnerDto getPrimaryOrganizerByPoll(PollDto pollDto, Boolean isPrimaryOrganizer);
    public List<PollOwnerDto> getAllByOrganizer(UserDto user);
    public PollOwnerDto getPollByOrganizer(UserDto user, PollDto poll);
}

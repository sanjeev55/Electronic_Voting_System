/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package charlie.logic;

import charlie.dto.ParticipantDto;
import charlie.dto.PollDto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Remote
public interface ParticipantLogic {
    public ParticipantDto getById(int participantId);
    public List<ParticipantDto> getAll();
    public List<ParticipantDto> getParticipantByPollId(int pollId);
    public ParticipantDto getParticipantByEmail(String email);
    public void createParticipant(ParticipantDto participantDto);
}

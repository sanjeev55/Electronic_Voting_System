/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package charlie.logic;

import charlie.dto.PollDto;
import charlie.dto.PollParticipantDto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Remote
public interface PollParticipantLogic {
    public List<PollParticipantDto> getParticipantsOfPoll(PollDto pollDto);
    public void savePollParticipant(PollParticipantDto pollParticipantDto);
}
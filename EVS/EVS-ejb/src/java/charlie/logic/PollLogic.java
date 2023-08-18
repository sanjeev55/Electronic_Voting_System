/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic;

import charlie.dto.PollDto;
import javax.ejb.Remote;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Remote
public interface PollLogic {
    public PollDto getPollById(int id);
    
    public PollDto getPollForEdit(String uuid);
    
    public void updatePoll(PollDto pollDto);
}
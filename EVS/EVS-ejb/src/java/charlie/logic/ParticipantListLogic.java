/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic;

import charlie.dto.ParticipantListDto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Remote
public interface ParticipantListLogic {
    public List<ParticipantListDto> getListByOriganizer(int organizerId);
    
    public void saveParticipantList(ParticipantListDto participantListDto);
    
    public void deleteParticipantListById(int listId);
    
    public ParticipantListDto getByParticipantListById(int id);
    
    public void updateParticipantList(ParticipantListDto participantListDto);
    
    public void deleteByOrganizerId(int organizerId);
}

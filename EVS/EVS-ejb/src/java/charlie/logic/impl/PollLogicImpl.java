/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.logic.impl;

import charlie.dao.PollAccess;
import charlie.dto.PollDto;
import charlie.entity.PollEntity;
import charlie.logic.PollLogic;
import charlie.mapper.PollEntityMapper;
import charlie.utils.DateUtils;
import java.util.Date;
import java.util.HashSet;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class PollLogicImpl implements PollLogic {
    
    @EJB
    private PollAccess pa;
    
    @EJB
    private PollEntityMapper pem;
    
    
    @Override
    public PollDto getPollById(int id) {
        PollEntity pe = pa.getPollById(id);
        return pem.toDto(pe);
        
    }
    
    @Override
    public PollDto getPollForEdit(String uuid){
    
        PollEntity pe = pa.getPollForEdit(uuid);
        System.out.println("POll Entity" + pe);
        return pem.toDto(pe);
    }
    
    @Override
    public void updatePoll(PollDto pollDto){
        String startDate = pollDto.getStartsAt();
        System.out.println("startDate====" + startDate);
        String endDate = pollDto.getEndsAt();
        System.out.print("End date=====" + endDate);
        System.out.println("PollDTO:" + pollDto);
        PollEntity pe = pem.toEntity(pollDto);
        pa.updatePoll(pe);
    }
    
}

package charlie.logic.impl;

import charlie.dto.PollDto;
import charlie.entity.PollStateEnum;
import charlie.response.Result;
import charlie.service.PollService;

import charlie.utils.StringUtils;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;


@Named(value = "pollManager")
@SessionScoped
public class PollManagerLogic implements Serializable{
    

    @EJB
    private PollService pollService;
    
    private PollDto pollInfo = new PollDto();
    
    
    public List<PollDto> getPolls() {
        return List.of(get("title1"), get("title2"), get("title3"));
    }
    
    
    private PollDto get(String pollName) {
        PollDto dto = new PollDto();
        dto.setId(1);
        dto.setTitle(pollName);
        dto.setDescription("kjdf");
        dto.setEndsAt(Instant.now().toString());
        dto.setStartsAt(Instant.now().toString());
        dto.setTrackParticipant(Boolean.TRUE);
        dto.setState(PollStateEnum.STARTED);
        dto.setPrimaryOrganizerId(101);
        return dto;
    }
    
    public void addPoll() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String startsAt = request.getParameter("startsAt");
        String endsAt = request.getParameter("endsAt");
        
        pollInfo.setStartsAt(startsAt);
        pollInfo.setEndsAt(endsAt);
        
        FacesContext context = FacesContext.getCurrentInstance();
        if (!StringUtils.hasText(pollInfo.getTitle())) {
            FacesMessage message = new FacesMessage("Title cannot be empty");
            context.addMessage("addPoll:title", message);
            return;
        }
        
        if (!StringUtils.hasText(pollInfo.getStartsAt())) {
            FacesMessage message = new FacesMessage("Poll start date cannot be empty");
            context.addMessage("addPoll:startsAt", message);
            return;
        }

        if (!StringUtils.hasText(pollInfo.getEndsAt())) {
            FacesMessage message = new FacesMessage("Poll end date cannot be empty");
            context.addMessage("addPoll:endsAt", message);
            return;
        }
        
        System.out.println("charlie.logic.impl.PollManagerLogic.addOrUpdatePoll()");
        System.out.println(this.pollInfo);
        
        Result<PollDto> result = pollService.save(this.pollInfo);
        if(result.isError()) {
            FacesMessage message = new FacesMessage(result.getError());
            context.addMessage("addPoll:title", message);
            return;
        }
    }

    public PollDto getPollInfo() {
        return pollInfo;
    }

    public void setPollInfo(PollDto pollInfo) {
        this.pollInfo = pollInfo;
    }
    
    
    
}

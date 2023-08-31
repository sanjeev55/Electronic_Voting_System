package charlie.web;

import charlie.dto.ParticipantListDto;
import charlie.dto.PollDto;
import charlie.dto.PollOwnerDto;
import charlie.dto.PollParticipantDto;
import charlie.dto.UserDto;
import charlie.entity.PollStateEnum;
import charlie.logic.ParticipantListLogic;
import charlie.logic.PollParticipantLogic;
import charlie.logic.UserLogic;
import charlie.utils.DateUtils;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import charlie.logic.PollLogic;
import charlie.logic.PollOwnerLogic;

@Named(value = "pollEditBean")
@ViewScoped
public class PollEditBean implements Serializable {

    @EJB
    private PollLogic pl;

    @EJB
    private UserLogic ul;

    @EJB
    private PollParticipantLogic ppl;
    
    @EJB
    private ParticipantListLogic pll;
    
    @EJB
    private PollOwnerLogic pol;
    

    private PollDto pollDto;
    private boolean success;
    private boolean failure;
    private boolean pollParticipantSuccess;
    private boolean pollParticipantFailure;
    private String failureMessage;
    private String pollParticipantFailureMessage;
    
    private List<ParticipantListDto> organizerParticipantListDto;
    private UserDto currentUser;
    private String selectedPollParticipants;

    private String pollUuid;
    
    private String selectedParticipants;
    private String newSelectedParticipants;
    private Map<String, String> processedOrganizerParticipantListDto;

    
    
    @PostConstruct
    public void init() {
        getPollUuid();
        getPollDto();
        getCurrentUser();
        getOrganizerParticipantListDto();
    }

    public String getPollUuid() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        pollUuid = params.get("pollUuid");
        return pollUuid;
    }

    public void setPollUuid(String pollUuid) {
        this.pollUuid = pollUuid;
    }

    public List<PollStateEnum> getAvailableStates() {
        return Arrays.asList(PollStateEnum.values());
    }

    public String saveChanges() {
        try {
            pl.updatePoll(pollDto);
            success = true;
            failure = false;
            return "managePollPage";
        } catch (EJBException e) {
            success = false;
            failure = true;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            failureMessage = t.getMessage();
            return null;
        }
    }

    public boolean isValid() {
        return pollUuid != null && getPollDto() != null;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return failure;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public PollDto getPollDto() {
        if (pollDto == null) {
            pollDto = pl.getPollForEdit(pollUuid);
            if(pollDto!=null){
                pollDto.setStartsAt(DateUtils.convertDateToCustomFormat(pollDto.getStartsAt()));
                pollDto.setEndsAt(DateUtils.convertDateToCustomFormat(pollDto.getEndsAt()));
            }         
        }
        return pollDto;
    }

    public void setPollDto(PollDto pollDto) {
        this.pollDto = pollDto;
    }

    public List<ParticipantListDto> getOrganizerParticipantListDto() {
        if(organizerParticipantListDto == null){
            organizerParticipantListDto = pll.getListByOriganizer(currentUser.getId());
            processedOrganizerParticipantListDto = convertToMap(getOrganizerParticipantListDto());
            System.out.println("processedorganizerParticipantListDto" + processedOrganizerParticipantListDto);
        }
        return organizerParticipantListDto;
    }

    public void setOrganizerParticipantListDto(List<ParticipantListDto> organizerParticipantListDto) {
        this.organizerParticipantListDto = organizerParticipantListDto;
    }

    public UserDto getCurrentUser() {
        if(currentUser == null){
            currentUser = ul.getCurrentUser();
        }
        return currentUser;
    }

    public void setCurrentUser(UserDto currentUser) {
        this.currentUser = currentUser;
    }

    public String getSelectedPollParticipants() {
        if(selectedPollParticipants == null){
        List<PollParticipantDto> pollParticipantDto = ppl.getParticipantsOfPoll(pollDto);
        
        selectedPollParticipants = pollParticipantDto.stream()
                                                  .map(PollParticipantDto::getEmail)
                                                  .collect(Collectors.joining(", "));
        System.out.println("SelectedPollParticipants" + selectedPollParticipants);
        }
        return selectedPollParticipants;
    }
    public void setSelectedPollParticipants(String selectedPollParticipants) {
        this.selectedPollParticipants = selectedPollParticipants;
    }
    
    public void updateEmailList() {
        
        newSelectedParticipants = String.join(", ", selectedParticipants);
        System.out.println("SelectedPollParticipants in updateEmaillist: " + newSelectedParticipants);
        
    }
    
    public void addParticipantsToPoll(){
        try{
            String allParticipants = safeConcatenate(newSelectedParticipants, selectedPollParticipants);
            Set<String> emails = new HashSet<>(Arrays.asList(allParticipants.split(",")));
            System.out.println("Emails:"+emails);
            
            for (String email : emails) {
                email=email.trim();
                if (!email.isEmpty() && !validateEmail(email)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format: " + email, null));
                }
            }
            
            pollDto = getPollDto();
            System.out.println("PollDTO for participants:" + pollDto);
            ppl.deleteByPoll(pollDto);
            
            for (String email : emails) {
                
                PollParticipantDto newPollParticipantDto = new PollParticipantDto();
                
                newPollParticipantDto.setEmail(email);
                newPollParticipantDto.setHasParticipated(false);
                newPollParticipantDto.setToken(UUID.randomUUID().toString());
                newPollParticipantDto.setPoll(pollDto);
                newPollParticipantDto.setUuid(UUID.randomUUID().toString());
                
                ppl.savePollParticipant(newPollParticipantDto);
                pollParticipantSuccess = true;
                pollParticipantFailure = false;
                selectedParticipants = null;
                selectedPollParticipants = null;
                newSelectedParticipants = null;            
                
            }
        }catch(ValidatorException e){
            pollParticipantSuccess = false;
            pollParticipantFailure = true;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            pollParticipantFailureMessage = t.getMessage();
        }
    }
    
    private boolean validateEmail(String email) {
        email = email.trim(); 
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    public boolean isPollParticipantSuccess() {
        return pollParticipantSuccess;
    }

    public void setPollParticipantSuccess(boolean pollParticipantSuccess) {
        this.pollParticipantSuccess = pollParticipantSuccess;
    }

    public boolean isPollParticipantFailure() {
        return pollParticipantFailure;
    }

    public void setPollParticipantFailure(boolean pollParticipantFailure) {
        this.pollParticipantFailure = pollParticipantFailure;
    }

    public String getPollParticipantFailureMessage() {
        return pollParticipantFailureMessage;
    }

    public void setPollParticipantFailureMessage(String pollParticipantFailureMessage) {
        this.pollParticipantFailureMessage = pollParticipantFailureMessage;
    }
    
    public String getNewSelectedParticipants() {
        return newSelectedParticipants;
    }

    public void setNewSelectedParticipants(String newSelectedParticipants) {
        this.newSelectedParticipants = newSelectedParticipants;
    }
    
    
    public String getSelectedParticipants() {
        System.out.println("getSelectedParticipants======" + selectedParticipants);
        return selectedParticipants;
    }

    public void setSelectedParticipants(String selectedParticipants) {
        this.selectedParticipants = selectedParticipants.replace("[", "").replace("]","");
    }
    
    public Map<String, String> convertToMap(List<ParticipantListDto> participantList) {
    Map<String, String> resultMap = new HashMap<>();

    for (ParticipantListDto participant : participantList) {
        String name = participant.getName();
        // Joining emails with a comma
        String emails = String.join(",", participant.getEmails());
        resultMap.put(name, emails);
    }

    return resultMap;
}

    public Map<String, String> getProcessedOrganizerParticipantListDto() {
        return processedOrganizerParticipantListDto;
    }

    public void setProcessedorganizerParticipantListDto(Map<String, String> processedOrganizerParticipantListDto) {
        this.processedOrganizerParticipantListDto = processedOrganizerParticipantListDto;
    }
    
    private String safeConcatenate(String str1, String str2) {
        if(str1 == null) str1 = "";
        if(str2 == null) str2 = "";

        if(str1.isEmpty()) return str2;
        if(str2.isEmpty()) return str1;

        return str1 + "," + str2;
    }
    
    public String getPrimaryOwner(){
        System.out.println("pollDto:" + pollDto);
        PollOwnerDto pollOwnerDto = pol.getPrimaryOrganizerByPoll(pollDto, true);
        if(pollOwnerDto == null){
            return null;
        }
        return pollOwnerDto.getUsername();
    }
    
    
}

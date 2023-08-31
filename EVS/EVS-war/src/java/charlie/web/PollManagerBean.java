package charlie.web;

import charlie.domain.Page;
import charlie.dto.PollDto;
import charlie.domain.PollPaginationRequest;
import charlie.domain.Result;
import charlie.logic.PollLogic;

import charlie.utils.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;

@Named(value = "pollManager")
@SessionScoped
public class PollManagerBean implements Serializable {

    private static final Logger logger = Logger.getLogger(PollManagerBean.class.getName());

    @EJB
    private PollLogic pollService;

    private PollDto pollInfo = new PollDto();
    private PollPaginationRequest paginationRequest = PollPaginationRequest.build();
    private List<Integer> pageNumberList;
    private int requestedPageSize;
    private String requestedSortBy;
    private String requestedSortOrder;
    private String pollId;
    private String filterPostOwner;
    private boolean renderErrorMessage;
    private String errorMessage;
    private Page<PollDto> pollsWithPagination;

    public Page<PollDto> getPollsWithPagination() {

        logger.log(Level.INFO, "Inside get all polls");
        logger.log(Level.INFO, "Pagination request: " + paginationRequest);

        paginationRequest.parseFilterPostOwner(filterPostOwner);
        this.pollsWithPagination = pollService.getAllWithPagination(paginationRequest);
        double totalPagesInDouble = (double) pollsWithPagination.getTotalCount() / pollsWithPagination.getPageSize();
        int totalPages = (int) Math.ceil(totalPagesInDouble);

        logger.log(Level.WARNING, pollsWithPagination.toString());

        this.pageNumberList = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            this.pageNumberList.add(i);
        }

        this.requestedPageSize = paginationRequest.getPageSize();
        this.requestedSortBy = paginationRequest.getSortBy();
        this.requestedSortOrder = paginationRequest.getSortOrder();
        return this.pollsWithPagination;
    }

    public void startPoll() {
        try {
            System.out.println("inside start poll");
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            pollId = params.get("id");
            System.out.println("pollId: " + pollId);

            var result = this.pollService.changePollStateToStarted(StringUtils.parseInteger(pollId));
            System.out.println("result: " + result);
            if (result.isError()) {
                this.renderErrorMessage = true;
                this.errorMessage = result.getError();
                return;
            }

            pollId = null;
            renderErrorMessage = false;
            errorMessage = null;
        } catch (Exception e) {
            this.renderErrorMessage = true;

            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            this.errorMessage = t.getMessage();
        }
    }
    
    public void deletePoll() {
        try {
            System.out.println("inside delete poll");
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            pollId = params.get("id");
            System.out.println("pollId: " + pollId);

            pollService.deletePollInfo(Integer.parseInt(pollId));         
            
            System.out.println("poll info deleted successfully");
            
            pollId = null;
            renderErrorMessage = false;
            errorMessage = null;
        } catch (Exception e) {
            this.renderErrorMessage = true;

            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            this.errorMessage = t.getMessage();
        }
    }

    public String addPoll() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String startsAt = request.getParameter("startsAt");
        String endsAt = request.getParameter("endsAt");

        pollInfo.setStartsAt(startsAt);
        pollInfo.setEndsAt(endsAt);

        FacesContext context = FacesContext.getCurrentInstance();
        if (!StringUtils.hasText(pollInfo.getTitle())) {
            FacesMessage message = new FacesMessage("Title cannot be empty");
            context.addMessage("addPoll:title", message);
            return null;
        }

        if (!StringUtils.hasText(pollInfo.getStartsAt())) {
            FacesMessage message = new FacesMessage("Poll start date cannot be empty");
            context.addMessage("addPoll:title", message);
            return null;
        }

        if (!StringUtils.hasText(pollInfo.getEndsAt())) {
            FacesMessage message = new FacesMessage("Poll end date cannot be empty");
            context.addMessage("addPoll:title", message);
            return null;
        }

        System.out.println("charlie.logic.impl.PollManagerLogic.addOrUpdatePoll()");
        System.out.println(this.pollInfo);

        Result<PollDto> result = pollService.save(this.pollInfo);
        if (result.isError()) {
            FacesMessage message = new FacesMessage(result.getError());
            context.addMessage("addPoll:title", message);
            return null;
        }

        this.pollInfo = new PollDto();
        return "/pages/user/manage_poll.xhtml?pageNumber=1&amp;pageSize=5&amp;sortOrder=DESC&amp;sortBy=updatedAt&amp;faces-redirect=true";
    }

    public PollDto getPollInfo() {
        return pollInfo;
    }

    public void setPollInfo(PollDto pollInfo) {
        this.pollInfo = pollInfo;
    }

    public PollPaginationRequest getPaginationRequest() {
        return paginationRequest;
    }

    public void setPaginationRequest(PollPaginationRequest paginationRequest) {
        this.paginationRequest = paginationRequest;
    }

    public List<Integer> getPageNumberList() {
        return pageNumberList;
    }

    public void setPageNumberList(List<Integer> pageNumberList) {
        this.pageNumberList = pageNumberList;
    }

    public int getRequestedPageSize() {
        return requestedPageSize;
    }

    public void setRequestedPageSize(int requestedPageSize) {
        this.requestedPageSize = requestedPageSize;
    }

    public String getRequestedSortBy() {
        return requestedSortBy;
    }

    public void setRequestedSortBy(String requestedSortBy) {
        this.requestedSortBy = requestedSortBy;
    }

    public String getRequestedSortOrder() {
        return requestedSortOrder;
    }

    public void setRequestedSortOrder(String requestedSortOrder) {
        this.requestedSortOrder = requestedSortOrder;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getFilterPostOwner() {
        return filterPostOwner;
    }

    public void setFilterPostOwner(String filterPostOwner) {
        this.filterPostOwner = filterPostOwner;
    }

    public boolean isRenderErrorMessage() {
        return renderErrorMessage;
    }

    public void setRenderErrorMessage(boolean renderErrorMessage) {
        this.renderErrorMessage = renderErrorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}

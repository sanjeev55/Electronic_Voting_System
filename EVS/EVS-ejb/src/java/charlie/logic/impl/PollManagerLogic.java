package charlie.logic.impl;

import charlie.domain.Page;
import charlie.dto.PollDto;
import charlie.domain.PaginationRequest;
import charlie.domain.PollPaginationRequest;
import charlie.domain.Result;
import charlie.service.PollService;

import charlie.utils.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;

@Named(value = "pollManager")
@SessionScoped
public class PollManagerLogic implements Serializable {

    private static final Logger logger = Logger.getLogger(PollManagerLogic.class.getName());

    @EJB
    private PollService pollService;

    private PollDto pollInfo = new PollDto();
    private PollPaginationRequest paginationRequest = PollPaginationRequest.build();
    private List<Integer> pageNumberList;
    private int requestedPageSize;
    private String requestedSortBy;
    private String requestedSortOrder;
    private String action;
    private String pollId;
    private String filterPostOwner;

    public Page<PollDto> getPolls() {
        logger.log(Level.INFO, "Inside get all polls");
        logger.log(Level.INFO, "Pagination request: " + paginationRequest);
        var context = FacesContext.getCurrentInstance();

        if (StringUtils.hasText(action) && action.equalsIgnoreCase("delete") && StringUtils.hasText(pollId)) {
            this.pollService.deleteById(StringUtils.parseInteger(pollId));
        }

        if (StringUtils.hasText(action) && action.equalsIgnoreCase("start_poll") && StringUtils.hasText(pollId)) {
            var result = this.pollService.changePollStateToStarted(StringUtils.parseInteger(pollId));
            if (result.isError()) {

                context.addMessage("errorMessage",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, result.getError(), result.getError()));
            }
        }

        paginationRequest.parseFilterPostOwner(filterPostOwner);
        Page<PollDto> allWithPagination = pollService.getAllWithPagination(paginationRequest);
        double totalPagesInDouble = (double) allWithPagination.getTotalCount() / allWithPagination.getPageSize();
        int totalPages = (int) Math.ceil(totalPagesInDouble);

        logger.log(Level.WARNING, allWithPagination.toString());

        this.pageNumberList = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            this.pageNumberList.add(i);
        }

        this.requestedPageSize = paginationRequest.getPageSize();
        this.requestedSortBy = paginationRequest.getSortBy();
        this.requestedSortOrder = paginationRequest.getSortOrder();

        return allWithPagination;
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

        return "/pages/user/manage_poll.xhtml?pageNumber=1&amp;pageSize=10&amp;sortOrder=DESC&amp;sortBy=updatedAt&amp;faces-redirect=true";
    }

    public PollDto getPollInfo() {
        return pollInfo;
    }

    public void setPollInfo(PollDto pollInfo) {
        this.pollInfo = pollInfo;
    }

    public PollService getPollService() {
        return pollService;
    }

    public void setPollService(PollService pollService) {
        this.pollService = pollService;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

}

package charlie.dao.filter;

import charlie.entity.PollStateEnum;
import charlie.entity.UserEntity;

public class PollSearchFilter {
    private UserEntity organizer;
    private PollStateEnum state;
    private Boolean filterPrimaryOrganizer;
    private int pageNumber;
    private int pageSize;
    private String sortField;
    private SearchOrderEnum sortOrder;

    public UserEntity getOrganizer() {
        return organizer;
    }

    public void setOrganizer(UserEntity organizer) {
        this.organizer = organizer;
    }


    public PollStateEnum getState() {
        return state;
    }

    public void setState(PollStateEnum state) {
        this.state = state;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SearchOrderEnum getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SearchOrderEnum sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getFilterPrimaryOrganizer() {
        return filterPrimaryOrganizer;
    }

    public void setFilterPrimaryOrganizer(Boolean filterPrimaryOrganizer) {
        this.filterPrimaryOrganizer = filterPrimaryOrganizer;
    }

    
    public PollSearchFilter() {
        this.pageNumber = 1;
        this.pageSize = 10;
        this.sortOrder = SearchOrderEnum.DESC;
        this.sortField = "createdAt";
    }

    @Override
    public String toString() {
        return "PollSearchFilter{" + "organizer=" + organizer + ", state=" + state + ", filterPrimaryOrganizer=" + filterPrimaryOrganizer + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", sortField=" + sortField + ", sortOrder=" + sortOrder + '}';
    }

    
}
package charlie.domain;

import charlie.utils.StringUtils;

public class PollPaginationRequest extends PaginationRequest {

    private Boolean filterPostOwner;
    
    public PollPaginationRequest(int pageNumber, int pageSize, String sortOrder, String sortBy) {
        super(pageNumber, pageSize, sortOrder, sortBy);
        this.filterPostOwner = Boolean.TRUE;
    }

    public Boolean getFilterPostOwner() {
        return filterPostOwner;
    }

    public void setFilterPostOwner(Boolean filterPostOwner) {
        this.filterPostOwner = filterPostOwner;
    }

    public void parseFilterPostOwner(String filterPostOwner) {
        if(!StringUtils.hasText(filterPostOwner))
            return;
        
        try {
            this.filterPostOwner = Boolean.parseBoolean(filterPostOwner);
        } catch (Exception e) {
            this.filterPostOwner = Boolean.TRUE;
        }
    }
    
     public static PollPaginationRequest build(int pageNumber, int pageSize, String sortOrder, String sortBy) {
        return new PollPaginationRequest(
                pageNumber,
                pageSize,
                StringUtils.hasText(sortOrder) ? sortOrder : "DESC",
                StringUtils.hasText(sortBy) ? sortBy : "updatedAt"
        );
    }

    public static PollPaginationRequest build() {
        return build(1, 5, null, null);
    }

    @Override
    public String toString() {
        return "PollPaginationRequest{" + "filterPostOwner=" + filterPostOwner + '}';
    }
    
    
}

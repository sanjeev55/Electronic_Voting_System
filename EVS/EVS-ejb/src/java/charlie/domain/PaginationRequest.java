package charlie.domain;

import charlie.utils.StringUtils;

public class PaginationRequest {
    private int pageNumber;
    private int pageSize;
    private String sortOrder;
    private String sortBy;

    public PaginationRequest(int pageNumber, int pageSize, String sortOrder, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortOrder = sortOrder;
        this.sortBy = sortBy;
    }

    public PaginationRequest() {
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

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public String toString() {
        return "PaginationRequest{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", sortOrder='" + sortOrder + '\'' +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }

    public static PaginationRequest build(int pageNumber, int pageSize, String sortOrder, String sortBy) {
        return new PaginationRequest(
                pageNumber,
                pageSize,
                StringUtils.hasText(sortOrder) ? sortOrder : "DESC",
                StringUtils.hasText(sortBy) ? sortBy : "updatedAt"
        );
    }

    public static PaginationRequest build() {
        return build(1, 10, null, null);
    }
}


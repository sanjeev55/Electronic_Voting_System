package charlie.domain;

import java.util.List;

public class Page<T> {

    private long totalCount;
    private int pageSize;
    private int pageNumber;
    private List<T> data;

    public Page() {
    }

    public Page(long totalCount, int pageSize, int pageNumber, List<T> data) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.data = data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Page{"
                + "totalCount=" + totalCount
                + ", pageSize=" + pageSize
                + ", pageNumber=" + pageNumber
                + ", data=" + data
                + '}';
    }

    public static <T> Page<T> build(long totalCount, int pageSize, int pageNumber, List<T> data) {
        return new Page<>(totalCount, pageSize, pageNumber, data);
    }
}

package charlie.dao.filter;

public enum SearchOrderEnum {
    ASC("ASC"),
    DESC("DESC");

    private String searchOrder;

    SearchOrderEnum(String searchOrder) {
        this.searchOrder = searchOrder;
    }

    public String getSearchOrder() {
        return this.searchOrder;
    }

    public static SearchOrderEnum resolveSearchOrder(String label) {
        for (SearchOrderEnum e : values()) {
            if (e.searchOrder.equals(label)) {
                return e;
            }
        }
        return SearchOrderEnum.DESC;
    }
}


package rs.vegait.timesheet.core.model;

public class Page<T> {
    private final Iterable<T> items;
    private final int pageSize;
    private final int pageNumber;
    private final int totalItems;

    public Page(Iterable<T> items, int pageNumber, int pageSize, int totalItems) {
        this.items = items;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalItems = totalItems;
    }

    public Iterable<T> items() {
        return this.items;
    }

    public int pageSize() {
        return this.pageSize;
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public int totalItems() {
        return this.totalItems;
    }
}

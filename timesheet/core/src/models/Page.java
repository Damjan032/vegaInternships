package models;

public class Page<T> {
    private Iterable<T> items;
    private int pageSize;
    private int pageNumber;
    private int totalItems;

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

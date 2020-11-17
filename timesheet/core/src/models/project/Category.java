package models.project;

public class Category {
    private String id;
    private String name;

    public Category(String id, String name) {
        if (id == null || name == null) {
            throw new ExceptionInInitializerError();
        }
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }
}

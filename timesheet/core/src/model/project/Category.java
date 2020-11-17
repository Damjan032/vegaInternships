package model.project;

import java.util.UUID;

public class Category {
    private UUID id;
    private String name;

    public Category(UUID id, String name) {
        if (id == null || name == null) {
            throw new ExceptionInInitializerError();
        }
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }
}

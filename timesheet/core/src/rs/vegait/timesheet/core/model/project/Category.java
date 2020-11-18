package rs.vegait.timesheet.core.model.project;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Category rename(String name) {
        return new Category(this.id, name);
    }
}

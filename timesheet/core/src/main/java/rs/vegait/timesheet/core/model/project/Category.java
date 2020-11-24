package rs.vegait.timesheet.core.model.project;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class Category {
    private UUID id;
    private String name;

    public Category(UUID id, @NotNull String name) {
        if (id == null || name == null || name.trim().equals("")) {
            throw new IllegalArgumentException();
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

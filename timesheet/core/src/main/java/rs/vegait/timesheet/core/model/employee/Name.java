package rs.vegait.timesheet.core.model.employee;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Name {
    private final String name;

    public Name(String name) {
        if (name == null || name.length() < 2 || name.length() > 50)
            throw new ExceptionInInitializerError();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String firstName() {
        return this.name.split(" ")[0];
    }

    public String lastName() {
        List<String> listNames = Arrays.asList(this.name.trim().split(" "));
        return String.join(" ", listNames.subList(1, listNames.size()));
    }
}

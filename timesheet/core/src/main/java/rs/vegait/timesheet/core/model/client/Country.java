package rs.vegait.timesheet.core.model.client;

import java.util.Objects;

public class Country {
    private String name;

    public Country(String name) {
        if (name == null)
            throw new ExceptionInInitializerError();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String name() {
        return name;
    }

}

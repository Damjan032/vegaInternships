package rs.vegait.timesheet.core.model.client;

import java.util.Objects;

public class City {
    private String name;
    private int postalCode;

    public City(String name, int postalCode) {
        if (name == null || postalCode <= 10)
            throw new ExceptionInInitializerError();
        this.name = name;
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return postalCode == city.postalCode &&
                Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, postalCode);
    }

    public String name() {
        return name;
    }

    public int postalCode() {
        return postalCode;
    }
}

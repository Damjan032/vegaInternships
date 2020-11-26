package rs.vegait.timesheet.core.model.client;

import java.util.Objects;

public class City {
    private final String name;
    private final int postalCode;

    public City(String name, int postalCode) {
        if (name == null || name.trim().length()==0)
            throw new IllegalArgumentException("City name is not in correct format");
        if (postalCode<1000)
            throw new IllegalArgumentException("PostalCode is not in correct format");
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

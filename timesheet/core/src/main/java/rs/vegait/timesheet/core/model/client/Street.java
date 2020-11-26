package rs.vegait.timesheet.core.model.client;

import java.util.Objects;

public class Street {
    private final String name;
    private final String number;

    public Street(String name, String number) {
        if (name == null || name.trim().length()==0)
            throw new IllegalArgumentException("Street name is not in valid format");
        this.name = name;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Objects.equals(name, street.name) &&
                Objects.equals(number, street.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

    public String name() {
        return name;
    }

    public String number() {
        return number;
    }
}

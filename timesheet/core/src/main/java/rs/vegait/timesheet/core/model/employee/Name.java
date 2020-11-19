package rs.vegait.timesheet.core.model.employee;

import java.util.Objects;

public class Name {
    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        if (firstName == null || lastName == null)
            throw new ExceptionInInitializerError();
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Name(Name name) {
        this.lastName = name.lastName;
        this.firstName = name.firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }
}

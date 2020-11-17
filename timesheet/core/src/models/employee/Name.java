package models.employee;

public class Name {
    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        if (firstName == null || lastName == null)
            throw new ExceptionInInitializerError();
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }
}

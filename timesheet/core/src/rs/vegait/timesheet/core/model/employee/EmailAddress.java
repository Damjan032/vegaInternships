package rs.vegait.timesheet.core.model.employee;

public class EmailAddress {
    public String emailAddress;

    public EmailAddress(String emailAddress) {
        if (emailAddress == null) {
            throw new ExceptionInInitializerError();
        }
        this.emailAddress = emailAddress;
    }

    public String emailAddress() {
        return emailAddress;
    }
}

package rs.vegait.timesheet.core.model.employee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAddress {
    public String emailAddress;

    public EmailAddress(String emailAddress) {
        if (emailAddress == null)
            throw new IllegalArgumentException("Email address is not in correct format");

        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return this.emailAddress;
    }
}

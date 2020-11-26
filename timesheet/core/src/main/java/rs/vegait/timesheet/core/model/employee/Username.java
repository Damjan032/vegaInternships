package rs.vegait.timesheet.core.model.employee;

public class Username {
    public String username;

    public Username(String username) {
        if (username == null || username.length()<5) {
            throw new IllegalArgumentException("Username is not valid ");
        }
        this.username = username;
    }

    @Override
    public String toString() {
        return this.username;
    }
}

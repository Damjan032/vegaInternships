package rs.vegait.timesheet.core.model.employee;

public class Username {
    public String username;

    public Username(String username) {
        if (username == null) {
            throw new ExceptionInInitializerError();
        }
        this.username = username;
    }

    @Override
    public String toString() {
        return this.username;
    }
}

package rs.vegait.timesheet.core.model.employee;

public class HashedPassword implements Password {

    private final String hashedPassword;

    public HashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String hashed() {
        return this.hashedPassword;
    }
}

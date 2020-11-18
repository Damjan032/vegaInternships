package rs.vegait.timesheet.core.model.employee;

public class Password {
    private String password;

    public Password(String password) {
        if (password == null || password.length()<6) {
            throw new ExceptionInInitializerError();
        }
        this.password = password;
    }

    public String password() {
        return password;
    }
}

package rs.vegait.timesheet.core.model.employee;

import rs.vegait.timesheet.core.model.HashingAlgorithm;

public class RawPassword implements Password {
    private final HashingAlgorithm hashingAlgorithm;
    private final String rawPassword;

    public RawPassword(String rawPassword, HashingAlgorithm hashingAlgorithm) {
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("Password is too week");
        }

        this.rawPassword = rawPassword;
        this.hashingAlgorithm = hashingAlgorithm;
    }

    @Override
    public String hashed() {
        return this.hashingAlgorithm.hash(this.rawPassword);
    }
}

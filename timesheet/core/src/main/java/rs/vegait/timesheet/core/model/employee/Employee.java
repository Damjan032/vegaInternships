package rs.vegait.timesheet.core.model.employee;

import rs.vegait.timesheet.core.model.HashingAlgorithm;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Employee {
    protected UUID id;
    protected Name name;
    protected Username username;
    protected Optional<Password> password;
    protected EmailAddress emailAddress;
    protected HoursPerWeek requiredHoursPerWeek;
    protected EmployeeStatus status;
    protected EmployeeRole role;
    protected boolean isAccepted;

    public Employee(@NotNull UUID id, @NotNull Name name, @NotNull Username username, @NotNull Optional<Password> password, @NotNull EmailAddress emailAddress, @NotNull HoursPerWeek requiredHoursPerWeek, @NotNull EmployeeStatus status, @NotNull EmployeeRole role, @NotNull Boolean isAccepted) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.emailAddress = emailAddress;
        this.requiredHoursPerWeek = requiredHoursPerWeek;
        this.status = status;
        this.role = role;
        this.isAccepted = isAccepted;
        this.password = password;
    }

    public UUID id() {
        return id;
    }

    public String username() {
        return this.username.toString();
    }

    public Name name() {
        return name;
    }

    public String emailAddress() {
        return this.emailAddress.toString();
    }

    public double requiredHoursPerWeek() {
        return requiredHoursPerWeek.hours();
    }

    public EmployeeStatus status() {
        return status;
    }

    public EmployeeRole role() {
        return role;
    }

    public boolean wasAccepted() {
        return this.isAccepted;
    }

    public Employee changeUsername(String newUsername) {
        return new Employee(this.id, this.name, new Username(newUsername), this.password, this.emailAddress, this.requiredHoursPerWeek, this.status, this.role, this.isAccepted);
    }

    public Employee changeEmailAddress(String newMailAddress) {
        return new Employee(this.id, this.name, this.username, this.password, new EmailAddress(newMailAddress), this.requiredHoursPerWeek, this.status, this.role, this.isAccepted);
    }

    public Employee changeName(Name name) {
        return new Employee(this.id, name, this.username, this.password, this.emailAddress, this.requiredHoursPerWeek, this.status, this.role, this.isAccepted);
    }

    public Employee changeHoursPerWeek(double newHoursPerWeek) {
        return new Employee(this.id, this.name, this.username, this.password, this.emailAddress, new HoursPerWeek(newHoursPerWeek), this.status, this.role, this.isAccepted);
    }

    public Employee changeRole(EmployeeRole newRole) {
        return new Employee(this.id, this.name, this.username, this.password, this.emailAddress, this.requiredHoursPerWeek, this.status, newRole, this.isAccepted);
    }

    public Employee changeStatus(EmployeeStatus newStatus) {
        return new Employee(this.id, this.name, this.username, this.password, this.emailAddress, this.requiredHoursPerWeek, newStatus, this.role, this.isAccepted);
    }

    public Employee acceptMail() {
        return new Employee(this.id, this.name, this.username, this.password, this.emailAddress, this.requiredHoursPerWeek, this.status, this.role, true);
    }

    public Employee resetPassword(String password, String repeatedPassword, HashingAlgorithm hashingAlgorithm) {
        if (!password.equals(repeatedPassword)) {
            throw new RuntimeException("Not same password");
        }

        return new Employee(this.id, this.name, this.username, Optional.of(new RawPassword(password, hashingAlgorithm)), this.emailAddress, this.requiredHoursPerWeek, this.status, this.role, true);
    }

    public boolean hasPassword() {
        return this.password.isPresent();
    }

    public String hashedPassword() {
        return this.password.get().hashed();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

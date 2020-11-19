package rs.vegait.timesheet.core.model.employee;

import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;

import javax.validation.constraints.NotNull;
import java.util.UUID;

;

public class Employee {
    protected UUID id;
    protected Name name;
    protected Username username;
    protected Password password;
    protected EmailAddress emailAddress;
    protected HoursPerWeek requiredHoursPerWeek;
    protected EmployeeStatus status;
    protected EmployeeRole role;
    protected DailyTimeSheet dailyTimeSheet;

    public Employee(UUID id, Name name, String password, String username, String emailAddress, double requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role) {
        this.id = id;
        this.name = name;
        this.password = new Password(password);
        this.username = new Username(username);
        this.emailAddress = new EmailAddress(emailAddress);
        this.requiredHoursPerWeek = new HoursPerWeek(requiredHoursPerWeek);
        this.status = status;
        this.role = role;
    }

    public Employee(@NotNull UUID id, @NotNull Name name, @NotNull Password password, @NotNull Username username, @NotNull EmailAddress emailAddress, @NotNull HoursPerWeek requiredHoursPerWeek, @NotNull EmployeeStatus status, @NotNull EmployeeRole role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
        this.emailAddress = emailAddress;
        this.requiredHoursPerWeek = requiredHoursPerWeek;
        this.status = status;
        this.role = role;
    }

    public UUID id() {
        return id;
    }

    public String username() {
        return username.username();
    }

    public Name name() {
        return name;
    }

    public String emailAddress() {
        return emailAddress.emailAddress();
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

    public String password() {
        return password.password();
    }

    public Employee changeUsername(String newUsername) {
        return new Employee(this.id, this.name, this.password, new Username(newUsername), this.emailAddress, this.requiredHoursPerWeek, this.status, this.role);
    }

    public Employee changeEmailAddress(String newMailAddress) {
        return new Employee(this.id, this.name, this.password, this.username, new EmailAddress(newMailAddress), this.requiredHoursPerWeek, this.status, this.role);
    }

    public Employee changeName(Name name) {
        return new Employee(this.id, new Name(name), this.password, this.username, this.emailAddress, this.requiredHoursPerWeek, this.status, this.role);
    }

    public Employee changePassword(String newPassword) {
        return new Employee(this.id, this.name, new Password(newPassword), this.username, this.emailAddress, this.requiredHoursPerWeek, this.status, this.role);
    }

    public Employee changeHoursPerWeek(double newHoursPerWeek) {
        return new Employee(this.id, this.name, this.password, this.username, this.emailAddress, new HoursPerWeek(newHoursPerWeek), this.status, this.role);
    }

    public Employee changeRole(EmployeeRole newRole) {
        return new Employee(this.id, this.name, this.password, this.username, this.emailAddress, this.requiredHoursPerWeek, this.status, newRole);
    }

    public Employee changeStatus(EmployeeStatus newStatus) {
        return new Employee(this.id, this.name, this.password, this.username, this.emailAddress, this.requiredHoursPerWeek, newStatus, this.role);
    }


}

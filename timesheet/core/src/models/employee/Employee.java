package models.employee;

import models.timesheet.DailyTimeSheet;

import java.util.UUID;

public class Employee {
    protected UUID id;
    protected Name name;
    protected Username username;
    protected EmailAddress emailAddress;
    protected HoursPerWeek requiredHoursPerWeek;
    protected EmployeeStatus status;
    protected EmployeeRole role;
    protected DailyTimeSheet dailyTimeSheet;

    public Employee(UUID id, Name name, String username, String emailAddress, double requiredHoursPerWeek, EmployeeStatus status, DailyTimeSheet dailyTimeSheet, EmployeeRole role) {
        this.id = id;
        this.name = name;
        this.username = new Username(username);
        this.emailAddress = new EmailAddress(emailAddress);
        this.requiredHoursPerWeek = new HoursPerWeek(requiredHoursPerWeek);
        this.dailyTimeSheet = dailyTimeSheet;
        this.status = status;
        this.role = role;
    }

    public Employee(UUID id, Username username, EmailAddress emailAddress, HoursPerWeek requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role, DailyTimeSheet dailyTimeSheet) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.requiredHoursPerWeek = requiredHoursPerWeek;
        this.status = status;
        this.role = role;
        this.dailyTimeSheet = dailyTimeSheet;
    }

    public UUID id() {
        return id;
    }

    public String username() {
        return username.username();
    }

    public Employee changeUsername(String newUsername){
        return new Employee(this.id, new Username(newUsername), this.emailAddress, this.requiredHoursPerWeek, this.status, this.role, this.dailyTimeSheet);
    }

    public String emailAddress() {
        return emailAddress.emailAddress();
    }

    public Employee changeEmailAddress(String newMailAddress){
        return new Employee(this.id, this.username, new EmailAddress(newMailAddress), this.requiredHoursPerWeek, this.status, this.role, this.dailyTimeSheet);
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
}

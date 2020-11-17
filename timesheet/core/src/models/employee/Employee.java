package models.employee;

import models.enums.EmployeeRole;
import models.enums.EmployeeStatus;
import models.timesheet.DailyTimeSheet;

public class Employee {
    protected String id;
    protected Username username;
    protected EmailAddress emailAddress;
    protected HoursPerWeek requiredHoursPerWeek;
    protected EmployeeStatus status;
    protected EmployeeRole role;
    protected DailyTimeSheet dailyTimeSheet;

    public Employee(String id, String username, String emailAddress, double requiredHoursPerWeek, EmployeeStatus status, DailyTimeSheet dailyTimeSheet, EmployeeRole role) {
        this.id = id;
        this.username = new Username(username);
        this.emailAddress = new EmailAddress(emailAddress);
        this.requiredHoursPerWeek = new HoursPerWeek(requiredHoursPerWeek);
        this.dailyTimeSheet = dailyTimeSheet;
        this.status = status;
        this.role = role;
    }

    public Employee(String id, Username username, EmailAddress emailAddress, HoursPerWeek requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role, DailyTimeSheet dailyTimeSheet) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.requiredHoursPerWeek = requiredHoursPerWeek;
        this.status = status;
        this.role = role;
        this.dailyTimeSheet = dailyTimeSheet;
    }

    public String id() {
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

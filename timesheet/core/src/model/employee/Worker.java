package model.employee;

import model.timesheet.DailyTimeSheet;

import java.util.UUID;

public class Worker extends Employee {
    public Worker(UUID id, Username username, EmailAddress emailAddress, HoursPerWeek requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role, DailyTimeSheet dailyTimeSheet) {
        super(id, username, emailAddress, requiredHoursPerWeek, status, EmployeeRole.WORKER, dailyTimeSheet);
    }
}

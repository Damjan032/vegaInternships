package rs.vegait.timesheet.core.model.employee;

import java.util.UUID;

public class Admin extends Employee {

    public Admin(UUID id, Name name, Username username, EmailAddress emailAddress, HoursPerWeek requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role) {
        super(id, name, username, emailAddress, requiredHoursPerWeek, status, EmployeeRole.ADMIN);
    }
}

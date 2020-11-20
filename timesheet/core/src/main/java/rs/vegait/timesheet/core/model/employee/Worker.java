package rs.vegait.timesheet.core.model.employee;

import java.util.UUID;

public class Worker extends Employee {

    public Worker(UUID id, Name name, Password password, Username username, EmailAddress emailAddress, HoursPerWeek requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role, Boolean isAccepted) {
        super(id, name, password, username, emailAddress, requiredHoursPerWeek, status, EmployeeRole.ADMIN, isAccepted);
    }
}

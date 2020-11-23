package rs.vegait.timesheet.core.model.employee;

import java.util.Optional;
import java.util.UUID;

public class Worker extends Employee {

    public Worker(UUID id, Name name, Optional<Password> password, Username username, EmailAddress emailAddress, HoursPerWeek requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role, Boolean isAccepted) {
        super(id, name, username, password, emailAddress, requiredHoursPerWeek, status, EmployeeRole.WORKER, isAccepted);
    }
}

package models.employee;

import models.enums.EmployeeRole;
import models.enums.EmployeeStatus;

public class Worker extends Employee {
    public Worker(String id, String username, String emailAddress, double requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role) {
        super(id, username, emailAddress, requiredHoursPerWeek, status, role);
        this.role = EmployeeRole.WORKER;
    }
}

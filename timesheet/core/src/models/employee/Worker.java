package models.employee;

public class Worker extends Employee {
    public Worker(String id, String username, String emailAddress, double requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role) {
        super(id, username, emailAddress, requiredHoursPerWeek, status, role);
        this.role = EmployeeRole.WORKER;
    }
}

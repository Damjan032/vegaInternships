package models.employee;

public class Admin extends Employee {
    public Admin(String id, String username, String emailAddress, double requiredHoursPerWeek, EmployeeStatus status, EmployeeRole role) {
        super(id, username, emailAddress, requiredHoursPerWeek, status, role);
        this.role = EmployeeRole.ADMIN;
    }
}

package rs.vegait.timesheet.core.repository;

import rs.vegait.timesheet.core.model.employee.Employee;

import java.sql.SQLException;
import java.util.Optional;

public interface EmployeeRepository extends CoreRepository<Employee> {
    Optional<Employee> isExistsEmailAndUsername(Employee employee) throws SQLException;
}

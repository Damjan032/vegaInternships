package rs.vegait.timesheet.core.repository;

import rs.vegait.timesheet.core.model.employee.EmailAddress;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.employee.Username;

import java.util.Optional;

public interface EmployeeRepository extends CoreRepository<Employee> {
    Optional<Employee> findByEmailOrUsername(EmailAddress emailAddress, Username username) throws Exception;
}

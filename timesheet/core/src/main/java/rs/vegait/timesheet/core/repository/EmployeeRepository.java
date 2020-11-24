package rs.vegait.timesheet.core.repository;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.EmailAddress;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.employee.Username;

import java.sql.SQLException;
import java.util.Optional;

public interface EmployeeRepository extends CoreRepository<Employee> {
    Optional<Employee> findByEmailOrUsername(EmailAddress emailAddress, Username username) throws Exception;

    Optional<Employee> findByName(String name) throws SQLException;

    Page<Employee> findBy(String searchString, char firstLetter, int pageNumber, int pageSize) throws SQLException;
}

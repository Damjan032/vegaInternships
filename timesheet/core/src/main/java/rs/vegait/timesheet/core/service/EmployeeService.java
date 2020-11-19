package rs.vegait.timesheet.core.service;


import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.repository.EmployeeRepository;

import java.sql.SQLException;
import java.util.UUID;

public class EmployeeService implements rs.vegait.timesheet.core.service.BaseService<Employee, UUID> {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void create(Employee employee) throws SQLException {
        var foundEmployee = this.employeeRepository.findById(employee.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.employeeRepository.add(employee);
    }


    @Override
    public void update(Employee updateObject) throws SQLException {
        var foundEmployee = this.employeeRepository.findById(updateObject.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.employeeRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws SQLException {
        var foundEmployee = this.employeeRepository.findById(id);
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }
        this.employeeRepository.remove(id);
    }

}

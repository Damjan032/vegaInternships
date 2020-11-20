package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.repository.EmployeeRepository;

import java.util.UUID;

public class EmployeeService implements rs.vegait.timesheet.core.service.BaseService<Employee, UUID> {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void create(Employee employee) throws Exception {
        var foundEmployee = this.employeeRepository.findById(employee.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.employeeRepository.add(employee);
    }


    @Override
    public void update(Employee updateObject) throws Exception {
        var foundEmployee = this.employeeRepository.findById(updateObject.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.employeeRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws Exception {
        var foundEmployee = this.employeeRepository.findById(id);
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }
        this.employeeRepository.remove(id);
    }

    public Iterable<Employee> findAll() throws Exception {
        return employeeRepository.findAll();
    }
}

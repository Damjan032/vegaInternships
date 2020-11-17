package service;

import model.employee.Employee;
import repository.EmployeeRepository;

import java.util.Optional;
import java.util.UUID;

public class EmployeeService implements IService<Employee, UUID> {

    private EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void create(Employee employee) {
        var foundEmployee = this.employeeRepository.findById(employee.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.employeeRepository.add(employee);
    }

    @Override
    public Optional<Employee> readById(UUID id) {
        return this.employeeRepository.findById(id);
    }

    @Override
    public void update(Employee updateObject) {
        var foundEmployee = this.employeeRepository.findById(updateObject.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.employeeRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) {
        var foundEmployee = this.employeeRepository.findById(id);
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }
        this.employeeRepository.remove(id);
    }
    
}

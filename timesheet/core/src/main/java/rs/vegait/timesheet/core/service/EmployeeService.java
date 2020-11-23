package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.EmailAddress;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.employee.Username;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;
import rs.vegait.timesheet.core.repository.EmployeeRepository;

import java.util.UUID;

@Component
public class EmployeeService implements rs.vegait.timesheet.core.service.BaseService<Employee, UUID> {

    private final EmployeeRepository employeeRepository;
    private final SMTPServer smtpServer;
    private final DailyTimeSheetRepository dailyTimeSheetRepository;

    public EmployeeService(EmployeeRepository employeeRepository, SMTPServer smtpServer, DailyTimeSheetRepository dailyTimeSheetRepository) {
        this.employeeRepository = employeeRepository;
        this.smtpServer = smtpServer;
        this.dailyTimeSheetRepository = dailyTimeSheetRepository;
    }

    @Override
    public void create(Employee employee) throws Exception {
        var foundEmployee = this.employeeRepository.findById(employee.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        if (this.employeeRepository.findByEmailOrUsername(
                new EmailAddress(employee.emailAddress()),
                new Username(employee.username())).isPresent()) {
            throw new RuntimeException("Already exists employee with same username or email");
        }
        String body = "http://localhost:8080/api/employees/activated?id=" + employee.id();
        this.smtpServer.sendEmail(employee.emailAddress(), "SSLEmail Testing Subject", body);
        this.employeeRepository.add(employee);
    }


    @Override
    public void update(Employee employee) throws Exception {
        var foundEmployee = this.employeeRepository.findById(employee.id());
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }

        if (this.employeeRepository.findByEmailOrUsername(
                new EmailAddress(employee.emailAddress()),
                new Username(employee.username()))
                .map(e -> !e.equals(employee))
                .orElse(false)) {
            throw new RuntimeException("Already exists employee  with same username or email");
        }
        this.employeeRepository.update(employee);
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

    public void setEmployeeActive(String id) throws Exception {
        var foundEmployee = this.employeeRepository.findById(UUID.fromString(id));
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }
        Employee employee = foundEmployee.get().acceptMail();
        this.employeeRepository.update(employee);

    }

    public Employee findById(String id) throws Exception {
        var foundEmployee = this.employeeRepository.findById(UUID.fromString(id));
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }
        return foundEmployee.get();
    }

    public Page<Employee> search(int pageNumber, int pageSize, String searchString, char firstLetter) throws Exception {
        return this.employeeRepository.findBy(searchString, firstLetter, pageNumber, pageSize);
    }
}

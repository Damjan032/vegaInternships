package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;
import rs.vegait.timesheet.core.repository.EmployeeRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class EmployeeService implements rs.vegait.timesheet.core.service.BaseService<Employee, UUID> {

    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final DailyTimeSheetRepository dailyTimeSheetRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmailService emailService, DailyTimeSheetRepository dailyTimeSheetRepository) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.dailyTimeSheetRepository = dailyTimeSheetRepository;
    }

    @Override
    public void create(Employee employee) throws Exception {

        var foundEmployee = this.employeeRepository.findById(employee.id());
        if (foundEmployee.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        if(this.employeeRepository.isExistsEmailAndUsername(employee).isPresent()){
            throw new RuntimeException("Already exists employee  with same username or email");
        }
        String body = "http://localhost:8080/api/employees/activated/"+employee.id();
        this.emailService.sendEmail(employee.emailAddress(),"SSLEmail Testing Subject", body);
        this.employeeRepository.add(employee);
    }


    @Override
    public void update(Employee updateObject) throws Exception {
        var foundEmployee = this.employeeRepository.findById(updateObject.id());
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }
        Optional<Employee> sameField = this.employeeRepository.isExistsEmailAndUsername(updateObject);
        if(sameField.isPresent()){
            if(!sameField.get().id().equals(updateObject.id()))
                throw new RuntimeException("Already exists employee  with same username or email");
        }
        this.employeeRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws Exception {
        var foundEmployee = this.employeeRepository.findById(id);
        if (!foundEmployee.isPresent()) {
            throw new RuntimeException("Non-existent employee");
        }
        var foundDailySheets = this.dailyTimeSheetRepository.findDailyTimeSheetsForEmployer(foundEmployee.get());
        AtomicBoolean isEmptyList = new AtomicBoolean(true);
        for (DailyTimeSheet dailyTimeSheet : foundDailySheets) {
            isEmptyList.set(false);
        }
        if (isEmptyList.get()) {
            return;
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
        Employee employee =foundEmployee.get().acceptMail();
        this.employeeRepository.update(employee);

    }
}

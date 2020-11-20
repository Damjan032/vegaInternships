package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.vegait.timesheet.api.dto.EmployeeDto;
import rs.vegait.timesheet.api.factory.EmployeeFactory;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.repository.EmployeeRepository;
import rs.vegait.timesheet.core.service.EmployeeService;

import java.util.UUID;

@RestController
@RequestMapping(value = "api/employees")
public class EmployeeController {
    private  final EmployeeService employeeService;
    private final EmployeeFactory employeeFactory;

    public EmployeeController(EmployeeService employeeService, EmployeeFactory employeeFactory) {
        this.employeeService = employeeService;
        this.employeeFactory = employeeFactory;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll() throws Exception {
        Iterable<Employee> employees = employeeService.findAll();
        employees.forEach(employee -> {
            System.out.println(employee.name().firstName());
        });
        System.out.println("SimpleEmail Start");
        final String password = "vwzlvoyqxkpvfdcq"; // correct password for gmail id
        employeeService.create(null);

       // EmailUtil.sendEmail("kmjofehwhjfzttyclg@mhzayt.com","SSLEmail Testing Subject", "Djes maco");

        return new ResponseEntity<String >("da", HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> add(@RequestBody EmployeeDto employeeDto) throws Exception {
        Employee newEmployee = employeeFactory.convertFromDtoForCreate(employeeDto);
        employeeService.create(newEmployee);
        return new ResponseEntity<EmployeeDto >(employeeDto, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> update(@RequestBody EmployeeDto employeeDto) throws Exception {
        Employee newEmployee = employeeFactory.convertFromDtoForUpdate(employeeDto);
        employeeService.update(newEmployee);
        return new ResponseEntity<EmployeeDto >(employeeDto, HttpStatus.OK);
    }

    @GetMapping(path = "activated/{id}")
    public ResponseEntity<String> activated(@PathVariable("id") String employeeId) throws Exception {
        this.employeeService.setEmployeeActive(employeeId);

        return new ResponseEntity<>("Now employee is active.", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteClinic(@PathVariable("id") String id) throws Exception {
        this.employeeService.delete(UUID.fromString(id));
        return new ResponseEntity<>("deleted clinic", HttpStatus.OK);
    }


}

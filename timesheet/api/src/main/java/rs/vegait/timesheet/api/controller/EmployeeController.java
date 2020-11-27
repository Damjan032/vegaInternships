package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.vegait.timesheet.api.dto.EmployeeDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.api.dto.ResetPasswordDto;
import rs.vegait.timesheet.api.factory.EmployeeFactory;
import rs.vegait.timesheet.core.model.HashingAlgorithm;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.repository.EmployeeRepository;
import rs.vegait.timesheet.core.service.EmployeeService;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeFactory employeeFactory;
    private final HashingAlgorithm hashingAlgorithm;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, EmployeeFactory employeeFactory, HashingAlgorithm hashingAlgorithm) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.employeeFactory = employeeFactory;
        this.hashingAlgorithm = hashingAlgorithm;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<EmployeeDto>> getAll() throws Exception {
        Iterable<Employee> employees = this.employeeRepository.findAll();
        return new ResponseEntity<>(employeeFactory.toListDto(employees), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody EmployeeDto employeeDto) throws Exception {
        Employee newEmployee = this.employeeFactory.createFromDto(UUID.randomUUID(), employeeDto);
        employeeService.create(newEmployee);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("api/employees/" + newEmployee.id()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PutMapping(path = {"/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> update(@PathVariable String id, @RequestBody EmployeeDto employeeDto) throws Exception {
        Employee employeeToUpdate = this.employeeFactory.createFromDto(UUID.fromString(id), employeeDto);
        employeeService.update(employeeToUpdate);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping(path = "/activated")
    public ResponseEntity<String> activated(@PathParam("id") String id) throws Exception {
        this.employeeService.setEmployeeActive(id);

        return new ResponseEntity<>("Now employee is active.", HttpStatus.OK);
    }

    @PostMapping(path = "/resetPassword")
    public ResponseEntity<String> resetPassword(@PathParam("id") String id, @RequestBody ResetPasswordDto resetPasswordDto) throws Exception {
        // Domain service - contains methods (logic) which is
        // Application service - use cases. Controller is actually application service!
        Employee employee = this.employeeService.findById(id);
        Employee withResetPassword = employee.resetPassword(resetPasswordDto.getPassword(), resetPasswordDto.getRepeatedPassword(), hashingAlgorithm);
        this.employeeRepository.update(withResetPassword);

        return new ResponseEntity<>("Now employee is active.", HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<PageDto<EmployeeDto>> getPage(@PathParam("pageNumber") Integer pageNumber,
                                                        @PathParam("pageSize") Integer pageSize) throws Exception {

        PageDto<EmployeeDto> employeeDtoPage = this.employeeFactory.toDtoPage(
                this.employeeService.search(pageNumber, pageSize, "", ' '));
        return new ResponseEntity<>(employeeDtoPage,
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable("id") String id) throws Exception {
        Employee employee = this.employeeService.findById(id);
        return new ResponseEntity<>(this.employeeFactory.toDto(employee), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception {
        this.employeeService.delete(UUID.fromString(id));
        return new ResponseEntity<>("deleted employee", HttpStatus.OK);
    }


}

package rs.vegait.timesheet.api.factory;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.EmployeeDto;
import rs.vegait.timesheet.core.model.employee.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class EmployeeFactory {
    public EmployeeFactory() {
    }

    public Employee convertFromDtoForCreate(EmployeeDto employeeDto) {
        List<String> name = Arrays.asList(employeeDto.getName().trim().split(" "));
        Name employeeName = new Name("FirstName", "LastName");
        String firstName = name.get(0);
        if (name.size() == 1) {
            employeeName = new Name(employeeDto.getName(), " ");
        } else {
            employeeName = new Name(firstName, String.join(" ", name.subList(1, name.size())));

        }
        return new Employee(UUID.randomUUID(),
                employeeName,
                new Password(employeeDto.getPassword()),
                new Username(employeeDto.getUsername()),
                new EmailAddress(employeeDto.getEmail()),
                new HoursPerWeek(employeeDto.getHoursPerWeek()),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER,
                false);
    }

    public Employee convertFromDtoForUpdate(EmployeeDto employeeDto) {
        List<String> name = Arrays.asList(employeeDto.getName().trim().split(" "));
        Name employeeName = new Name("FirstName", "LastName");
        String firstName = name.get(0);
        if (name.size() == 1) {
            employeeName = new Name(employeeDto.getName(), " ");
        } else {
            employeeName = new Name(firstName, String.join(" ", name.subList(1, name.size())));

        }
        return new Employee(UUID.fromString(employeeDto.getId()),
                employeeName,
                new Password(employeeDto.getPassword()),
                new Username(employeeDto.getUsername()),
                new EmailAddress(employeeDto.getEmail()),
                new HoursPerWeek(employeeDto.getHoursPerWeek()),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER,
                false);
    }
}

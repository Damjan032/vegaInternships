package rs.vegait.timesheet.api.factory;

import lombok.Data;
import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.EmployeeDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Component
public class EmployeeFactory implements Factory<EmployeeDto, Employee> {

    @Override
    public Employee createFromDto(UUID id, EmployeeDto employeeDto) {
        return new Employee(id,
                new Name(employeeDto.getName()),
                new Username(employeeDto.getUsername()),
                Optional.empty(),
                new EmailAddress(employeeDto.getEmail()),
                new HoursPerWeek(employeeDto.getHoursPerWeek()),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER,
                false);
    }

    @Override
    public EmployeeDto toDto(Employee employee) {
        String name = employee.name().firstName() + " " + employee.name().lastName();
        return new EmployeeDto(employee.id().toString(), employee.username(), name, employee.requiredHoursPerWeek(), employee.emailAddress(),
                employee.role(), employee.status(), employee.wasAccepted(), null);

    }

    @Override
    public List<EmployeeDto> toListDto(Iterable<Employee> iterable) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        iterable.forEach(employee -> {
            employeeDtoList.add(this.toDto(employee));
        });
        return employeeDtoList;
    }

    @Override
    public PageDto<EmployeeDto> toDtoPage(Page<Employee> page) {

        return new PageDto<>(this.toListDto(page.items()),
                page.pageSize(),
                page.pageNumber(),
                page.totalItems()
        );
    }
}

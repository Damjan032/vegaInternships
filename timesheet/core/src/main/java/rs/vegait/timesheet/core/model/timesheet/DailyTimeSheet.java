package rs.vegait.timesheet.core.model.timesheet;

import rs.vegait.timesheet.core.model.employee.Employee;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class DailyTimeSheet {
    private UUID id;
    private Employee employee;
    private Date day;

    public DailyTimeSheet(UUID id, @NotNull Employee employee, @NotNull Date day) {
        this.id = id;
        this.employee = employee;
        this.day = day;
    }

    public UUID id() {
        return id;
    }

    public Employee employee() {
        return employee;
    }

    public Date day() {
        return day;
    }
}

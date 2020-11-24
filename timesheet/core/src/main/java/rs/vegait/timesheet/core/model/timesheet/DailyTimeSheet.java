package rs.vegait.timesheet.core.model.timesheet;

import rs.vegait.timesheet.core.model.employee.Employee;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class DailyTimeSheet {
    private final UUID id;
    private final Employee employee;
    private final Date day;
    private final Iterable<TimeSheet> timeSheets;

    public DailyTimeSheet(UUID id, @NotNull Employee employee, @NotNull Date day, Iterable<TimeSheet> timeSheets) {
        this.id = id;
        this.employee = employee;
        this.day = day;
        this.timeSheets = timeSheets;
    }

    public UUID id() {
        return id;
    }

    public Iterable<TimeSheet> timeSheets() {
        return timeSheets;
    }

    public Employee employee() {
        return employee;
    }

    public Date day() {
        return day;
    }

    public DailyTimeSheet changeTimeSheets(Iterable<TimeSheet> timeSheets) {
        return new DailyTimeSheet(this.id, this.employee, this.day, timeSheets);
    }
}

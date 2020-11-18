package rs.vegait.timesheet.core.model.timesheet;

import rs.vegait.timesheet.core.model.employee.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DailyTimeSheet {
    private UUID id;
    private Employee employee;
    private List<TimeSheet> timeSheetList;

    public DailyTimeSheet(UUID id, Employee employee, Iterable<TimeSheet> timeSheetIterable) {

        this.id = id;
        this.employee = employee;
        this.timeSheetList = new ArrayList<TimeSheet>();
        timeSheetIterable.forEach(this.timeSheetList::add);

    }

    public DailyTimeSheet(UUID id, Employee employee) {
        this.id = id;
        this.employee = employee;
        this.timeSheetList = new ArrayList<>();
    }

    public UUID id() {
        return id;
    }

    public Employee employee() {
        return employee;
    }

    public List<TimeSheet> timeSheetList() {
        return timeSheetList;
    }
}

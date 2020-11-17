package models.timesheet;

import models.employee.Employee;

import java.util.ArrayList;
import java.util.List;

public class DailyTimeSheet {
    private int id;
    private Employee employee;
    private List<TimeSheet> timeSheetList;

    public DailyTimeSheet(int id, Employee employee, List<TimeSheet> timeSheetList) {
        this.id = id;
        this.employee = employee;
        this.timeSheetList = timeSheetList;
    }

    public DailyTimeSheet(int id, Employee employee) {
        this.id = id;
        this.employee = employee;
        this.timeSheetList = new ArrayList<>();
    }

    public int id() {
        return id;
    }

    public Employee employee() {
        return employee;
    }

    public List<TimeSheet> timeSheetList() {
        return timeSheetList;
    }
}

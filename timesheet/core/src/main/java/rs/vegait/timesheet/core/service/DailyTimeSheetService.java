package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheetResultSet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DailyTimeSheetService implements BaseService<DailyTimeSheet, UUID> {

    private final DailyTimeSheetRepository dailyTimeSheetRepository;
    private final EmployeeService employeeService;

    public DailyTimeSheetService(DailyTimeSheetRepository dailyTimeSheetRepository, EmployeeService employeeService) {
        this.dailyTimeSheetRepository = dailyTimeSheetRepository;
        this.employeeService = employeeService;
    }

    @Override
    public void create(DailyTimeSheet dailyTimeSheet) throws Exception {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(dailyTimeSheet.id());
        if (foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.dailyTimeSheetRepository.add(dailyTimeSheet);
    }


    @Override
    public void update(DailyTimeSheet updateObject) throws Exception {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(updateObject.id());
        if (foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.dailyTimeSheetRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws Exception {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(id);
        if (!foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Non-existent dailyTimeSheet");
        }
        this.dailyTimeSheetRepository.remove(id);
    }

    public Iterable<TimeSheetResultSet> findTimeSheetSet(String employeeId, Date dateFrom, Date dateTo) throws Exception {
      /* Employee employee = this.employeeService.findById(employeeId);
        List<TimeSheetResultSet> timeSheetResultSetList = new ArrayList<>();
        Iterable<DailyTimeSheet> dailyTimeSheets = this.dailyTimeSheetRepository.findDailyTimeSheetsForEmployee(employee, dateFrom, dateTo);
        for (DailyTimeSheet dailyTimeSheet : dailyTimeSheets) {
            Iterable<TimeSheet> timeSheets = this.timeSheetRepository.findDailyTimeSheetsForDailySheet(dailyTimeSheet.id().toString());
            timeSheetResultSetList.add(new TimeSheetResultSet(dailyTimeSheet, timeSheets, dailyHoursOfWork(timeSheets)));
        }
        return timeSheetResultSetList;*/
        return null;
    }

    public double dailyHoursOfWork(Iterable<TimeSheet> timeSheets) {
        double time = 0;
        for (TimeSheet timeSheet : timeSheets) {
            time += timeSheet.time().numberOfHours();
            if (timeSheet.hasOvertime()) time += timeSheet.overtime().numberOfHours();
        }
        return time;
    }

    public Optional<DailyTimeSheet> findByEmployeeAndData(String employeeId, String date) throws Exception {
        Employee employee = this.employeeService.findById(employeeId);
        if(employee==null){
            throw new RuntimeException("Non-existing employeeId");
        }
        return  this.dailyTimeSheetRepository.findByEmployeeAndDay(employee,new SimpleDateFormat("yyyy-MM-dd").parse(date));
    }
}

package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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

    public Iterable<DailyTimeSheet> findTimeSheetSet(String employeeId, Date dateFrom, Date dateTo) throws Exception {
        Employee employee = this.employeeService.findById(employeeId);
        Iterable<DailyTimeSheet> dailyTimeSheets = this.dailyTimeSheetRepository.findDailyTimeSheetsForEmployee(employee, dateFrom, dateTo);
        return dailyTimeSheets;
    }

    public double dailyHoursOfWork(Iterable<TimeSheet> timeSheets) {
        double time = 0;
        for (TimeSheet timeSheet : timeSheets) {
            time += timeSheet.time().numberOfHours();
            if (timeSheet.hasOvertime()) time += timeSheet.overtime().numberOfHours();
        }
        return time;
    }

    public Optional<DailyTimeSheet> findByEmployeeAndData(String employeeId, Date date) throws Exception {
        Employee employee = this.employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Non-existing employeeId");
        }
        return this.dailyTimeSheetRepository.findByEmployeeAndDay(employee, date);
    }

    public void createOrUpdate(DailyTimeSheet dailyTimeSheet, Employee employee) throws Exception {
        Optional<DailyTimeSheet> dailyTimeSheetOptional = this.findByEmployeeAndData(employee.id().toString(), dailyTimeSheet.day());
        if (dailyTimeSheetOptional.isPresent()) {
            DailyTimeSheet dailyTimeSheetForUpdate = dailyTimeSheetOptional.get().changeTimeSheets(dailyTimeSheet.timeSheets());
            this.dailyTimeSheetRepository.update(dailyTimeSheetForUpdate);
        } else {
            this.dailyTimeSheetRepository.add(new DailyTimeSheet
                    (UUID.randomUUID(), employee, dailyTimeSheet.day(), dailyTimeSheet.timeSheets()));
        }

    }
}

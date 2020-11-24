package rs.vegait.timesheet.core.repository;


import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;

import java.util.Date;
import java.util.Optional;

public interface DailyTimeSheetRepository extends CoreRepository<DailyTimeSheet> {
    Iterable<DailyTimeSheet> findDailyTimeSheetsForEmployee(Employee employee, Date dateFrom, Date dateTo) throws Exception;

    Optional<DailyTimeSheet> findByEmployeeAndDay(Employee employee, Date date) throws Exception;
}

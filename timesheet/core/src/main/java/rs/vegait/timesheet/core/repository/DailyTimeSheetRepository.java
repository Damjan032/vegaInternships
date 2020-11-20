package rs.vegait.timesheet.core.repository;


import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;

import java.sql.SQLException;

public interface DailyTimeSheetRepository extends CoreRepository<DailyTimeSheet> {
    Iterable<DailyTimeSheet> findDailyTimeSheetsForEmployer(Employee employee) throws SQLException;
}

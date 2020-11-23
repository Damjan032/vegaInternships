package rs.vegait.timesheet.core.repository;

import rs.vegait.timesheet.core.model.timesheet.TimeSheet;

public interface TimeSheetRepository extends CoreRepository<TimeSheet> {
    Iterable<TimeSheet> findDailyTimeSheetsForDailySheet(String dailyTimeSheetId) throws Exception;
}

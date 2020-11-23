package rs.vegait.timesheet.core.model.timesheet;

import javax.validation.constraints.NotNull;

public class TimeSheetResultSet {
    @NotNull
    private final DailyTimeSheet dailyTimeSheet;
    @NotNull
    private final Iterable<TimeSheet> timeSheets;
    @NotNull
    private final double time;

    public TimeSheetResultSet(DailyTimeSheet dailyTimeSheet, Iterable<TimeSheet> timeSheets, double time) {
        this.dailyTimeSheet = dailyTimeSheet;
        this.timeSheets = timeSheets;
        this.time = time;
    }

    public DailyTimeSheet dailyTimeSheet() {
        return dailyTimeSheet;
    }

    public Iterable<TimeSheet> timeSheets() {
        return timeSheets;
    }

    public double dailyTimeOfWorks() {
        return time;
    }
}

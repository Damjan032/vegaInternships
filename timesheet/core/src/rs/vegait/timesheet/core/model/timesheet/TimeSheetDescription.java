package rs.vegait.timesheet.core.model.timesheet;

public class TimeSheetDescription {
    public String description;

    public TimeSheetDescription(String description) {
        if (description == null) {
            throw new ExceptionInInitializerError();
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}

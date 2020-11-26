package rs.vegait.timesheet.core.model.timesheet;

public class TimeSheetDescription {
    public String description;

    public TimeSheetDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description is not in valid format");
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}

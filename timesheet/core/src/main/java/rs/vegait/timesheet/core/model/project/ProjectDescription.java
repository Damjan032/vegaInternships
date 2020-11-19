package rs.vegait.timesheet.core.model.project;

public class ProjectDescription {
    private String description;

    public ProjectDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }

        this.description = description;
    }

    public String description() {
        return description;
    }
}

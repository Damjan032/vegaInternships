package rs.vegait.timesheet.core.model.project;

public class ProjectName {
    private final String name;

    public ProjectName(String name) {
        if (name == null || name.trim().length()==0)
            throw new IllegalArgumentException("Project name is not in correct format");
        this.name = name;
    }

    public String name() {
        return name;
    }
}

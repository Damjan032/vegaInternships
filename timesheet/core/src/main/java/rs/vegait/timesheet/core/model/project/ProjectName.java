package rs.vegait.timesheet.core.model.project;

public class ProjectName {
    private String name;

    public ProjectName(String name) {
        if (name == null) {
            throw new ExceptionInInitializerError();
        }
        this.name = name;
    }

    public String name() {
        return name;
    }
}

package model.project;

public class ProjectDescription {
    public String description;

    public ProjectDescription(String description) {
        if (description == null) {
            throw new ExceptionInInitializerError();
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}

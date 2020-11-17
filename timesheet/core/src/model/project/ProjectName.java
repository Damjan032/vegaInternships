package model.project;

public class ProjectName {
    public String name;

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

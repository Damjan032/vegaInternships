package rs.vegait.timesheet.core.model.project;

import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.model.employee.Employee;

import java.util.UUID;

public class Project {
    protected UUID id;
    protected ProjectDescription description;
    protected ProjectName name;
    protected ProjectStatus status;
    protected Employee teamLead;
    protected Client client;
    protected Category category;

    public Project(UUID id, String projectDescription, String projectName, Client client,Category category, ProjectStatus projectStatus, Employee teamLead) {
        this.id = id;
        this.description = new ProjectDescription(projectDescription);
        this.name = new ProjectName(projectName);
        this.category = category;
        this.status = projectStatus;
        this.client = client;
        this.teamLead = teamLead;
    }

    public UUID id() {
        return id;
    }

    public ProjectDescription description() {
        return description;
    }

    public ProjectName name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public ProjectStatus status() {
        return status;
    }

    public Employee teamLead() {
        return teamLead;
    }
}

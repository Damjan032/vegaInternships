package models.project;

import models.client.Client;
import models.employee.Employee;
import models.enums.ProjectStatus;

public class Project {
    protected String id;
    protected ProjectDescription description;
    protected ProjectName name;
    protected Category category;
    protected ProjectStatus status;
    protected Employee teamLead;
    protected Client client;

    public Project(String id, String projectDescription, String projectName, Client client,Category category, ProjectStatus projectStatus, Employee teamLead) {
        this.id = id;
        this.description = new ProjectDescription(projectDescription);
        this.name = new ProjectName(projectName);
        this.category = category;
        this.status = projectStatus;
        this.client = client;
        this.teamLead = teamLead;
    }

    public String id() {
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

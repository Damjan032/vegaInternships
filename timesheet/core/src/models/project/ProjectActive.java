package models.project;

import models.employee.Employee;

public class ProjectActive extends Project {
    public ProjectActive(String id, String projectDescription, String projectName, Category category, Employee teamLead) {
        super(id, projectDescription, projectName, category, ProjectStatus.ACTIVE, teamLead);
    }
}

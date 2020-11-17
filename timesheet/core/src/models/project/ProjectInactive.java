package models.project;

import models.employee.Employee;
import models.enums.ProjectStatus;

public class ProjectInactive extends Project {
    public ProjectInactive(String id, String projectDescription, String projectName, Category category, Employee teamLead) {
        super(id, projectDescription, projectName, category, ProjectStatus.INACTIVE, teamLead);
    }
}

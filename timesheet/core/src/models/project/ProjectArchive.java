package models.project;

import models.employee.Employee;

public class ProjectArchive extends Project {
    public ProjectArchive(String id, String projectDescription, String projectName, Category category, Employee teamLead) {
        super(id, projectDescription, projectName, category, ProjectStatus.ARCHIVE, teamLead);
    }
}

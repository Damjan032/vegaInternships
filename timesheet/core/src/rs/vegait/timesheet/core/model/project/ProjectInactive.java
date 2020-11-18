package rs.vegait.timesheet.core.model.project;

import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.model.employee.Employee;

import java.util.UUID;

public class ProjectInactive extends Project {
    public ProjectInactive(UUID id, String projectDescription, String projectName, Client client, Category category, ProjectStatus projectStatus, Employee teamLead) {
        super(id, projectDescription, projectName, client, category, ProjectStatus.INACTIVE, teamLead);
    }
}

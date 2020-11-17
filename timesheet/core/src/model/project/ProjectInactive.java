package model.project;

import model.client.Client;
import model.employee.Employee;

import java.util.UUID;

public class ProjectInactive extends Project {
    public ProjectInactive(UUID id, String projectDescription, String projectName, Client client, Category category, ProjectStatus projectStatus, Employee teamLead) {
        super(id, projectDescription, projectName, client, category, ProjectStatus.INACTIVE, teamLead);
    }
}

package rs.vegait.timesheet.core.model.project;

import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.model.employee.Employee;

import java.util.Optional;
import java.util.UUID;

public class ProjectArchive extends Project {
    public ProjectArchive(UUID id, Optional<ProjectDescription> description, ProjectName name, ProjectStatus status, Employee teamLead, Client client) {
        super(id, description, name, ProjectStatus.ARCHIVE, teamLead, client);
    }
}

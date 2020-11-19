package rs.vegait.timesheet.core.model.project;

import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.model.employee.Employee;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public class Project {
    protected UUID id;
    protected Optional<ProjectDescription> description;
    protected ProjectName name;
    protected ProjectStatus status;
    protected Employee teamLead;
    protected Client client;
    protected Category category;

    public Project(@NotNull UUID id, Optional<ProjectDescription> description, @NotNull ProjectName name, @NotNull ProjectStatus status, @NotNull Employee teamLead, @NotNull Client client, @NotNull Category category) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.status = status;
        this.teamLead = teamLead;
        this.client = client;
        this.category = category;
    }

    public UUID id() {
        return id;
    }

    public ProjectDescription description() {
        return description.get();
    }

    public boolean hasDescription() {
        return description.isPresent();
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

    public Client client() {
        return client;
    }

    public Project changeDescription(ProjectDescription description) {
        return new Project(this.id, Optional.of(description), this.name, this.status, this.teamLead, this.client, this.category);
    }

    public Project rename(ProjectName name) {
        return new Project(this.id, this.description, name, this.status, this.teamLead, this.client, this.category);
    }

    public Project changeStatus(ProjectStatus status) {
        return new Project(this.id, this.description, this.name, status, this.teamLead, this.client, this.category);
    }

    public Project updateTeamLead(Employee teamLead) {
        return new Project(this.id, this.description, this.name, this.status, teamLead, this.client, this.category);
    }

    public Project updateClient(Client client) {
        return new Project(this.id, this.description, this.name, this.status, this.teamLead, client, this.category);
    }

    public Project updateCategory(Category category) {
        return new Project(this.id, this.description, this.name, this.status, this.teamLead, this.client, category);
    }
}

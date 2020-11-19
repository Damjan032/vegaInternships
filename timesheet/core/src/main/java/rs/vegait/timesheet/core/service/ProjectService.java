package rs.vegait.timesheet.core.service;


import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.repository.ProjectRepository;

import java.sql.SQLException;
import java.util.UUID;

public class ProjectService implements BaseService<Project, UUID> {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void create(Project project) throws SQLException {
        var foundProject = this.projectRepository.findById(project.id());
        if (foundProject.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.projectRepository.add(project);
    }


    @Override
    public void update(Project updateObject) throws SQLException {
        var foundProject = this.projectRepository.findById(updateObject.id());
        if (foundProject.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.projectRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws SQLException {
        var foundProject = this.projectRepository.findById(id);
        if (!foundProject.isPresent()) {
            throw new RuntimeException("Non-existent project");
        }
        this.projectRepository.remove(id);
    }
}

package service;

import model.project.Project;
import repository.ProjectRepository;

import java.util.Optional;
import java.util.UUID;

public class ProjectService implements IService<Project, UUID> {

    private ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void create(Project project) {
        var foundProject = this.projectRepository.findById(project.id());
        if (foundProject.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.projectRepository.add(project);
    }

    @Override
    public Optional<Project> readById(UUID id) {
        return this.projectRepository.findById(id);
    }

    @Override
    public void update(Project updateObject) {
        var foundProject = this.projectRepository.findById(updateObject.id());
        if (foundProject.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.projectRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) {
        var foundProject = this.projectRepository.findById(id);
        if (!foundProject.isPresent()) {
            throw new RuntimeException("Non-existent project");
        }
        this.projectRepository.remove(id);
    }
}

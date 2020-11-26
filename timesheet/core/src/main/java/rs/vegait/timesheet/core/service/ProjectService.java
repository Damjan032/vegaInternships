package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.repository.ProjectRepository;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.ConfigurationException;
import java.security.InvalidKeyException;
import java.util.UUID;

@Component
public class ProjectService implements BaseService<Project, UUID> {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void create(Project project) throws Exception {
        var foundProject = this.projectRepository.findById(project.id());
        if (foundProject.isPresent()) {
            throw new InstanceAlreadyExistsException("Already exists project with same id");
        }

        this.projectRepository.add(project);
    }

    @Override
    public void update(Project updateObject) throws Exception {
        var foundProject = this.projectRepository.findById(updateObject.id());
        if (!foundProject.isPresent()) {
            throw new InvalidKeyException("Non-existent project");
        }
        this.projectRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws Exception {
        var foundProject = this.projectRepository.findById(id);
        if (!foundProject.isPresent()) {
            throw new InvalidKeyException("Non-existent project");
        }
        this.projectRepository.remove(id);
    }

    public Iterable<Project> findAll() throws Exception {
        return this.projectRepository.findAll();
    }

    public Project findById(String id) throws Exception {
        var foundProject = this.projectRepository.findById(UUID.fromString(id));
        if (!foundProject.isPresent()) {
            throw new InvalidKeyException("Non-existent project");
        }
        return foundProject.get();
    }

    public Page<Project> search(int pageNumber, int pageSize, String searchString, Character firstLetter) throws Exception {
        return this.projectRepository.findBy(searchString == null ? " " : searchString, firstLetter == null ? ' ' : firstLetter, pageNumber, pageSize);
    }
}

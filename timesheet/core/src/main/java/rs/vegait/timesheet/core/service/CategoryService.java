package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.repository.CategoryRepository;
import rs.vegait.timesheet.core.repository.ProjectRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CategoryService implements rs.vegait.timesheet.core.service.BaseService<Category, UUID> {

    private final CategoryRepository categoryRepository;
    private final ProjectRepository projectRepository;

    public CategoryService(CategoryRepository categoryRepository, ProjectRepository projectRepository) {
        this.categoryRepository = categoryRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void create(Category category) throws Exception {
        var foundCategory = this.categoryRepository.findById(category.id());
        if (foundCategory.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        if (this.categoryRepository.findByName(category.name()).isPresent()) {
            throw new RuntimeException("Already exists employee  with same name");
        }
        this.categoryRepository.add(category);
    }

    @Override
    public void update(Category updateObject) throws Exception {
        var foundCategory = this.categoryRepository.findById(updateObject.id());
        if (!foundCategory.isPresent()) {
            throw new RuntimeException("Non-exists");
        }
        Optional<Category> sameField = this.categoryRepository.findByName(updateObject.name());
        if (sameField.isPresent()) {
            if (!sameField.get().id().equals(updateObject.id()))
                throw new RuntimeException("Already exists category with same name");
        }
        this.categoryRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws Exception {
        var foundCategory = this.categoryRepository.findById(id);
        if (!foundCategory.isPresent()) {
            throw new RuntimeException("Non-existent category");
        }

        this.categoryRepository.remove(id);
    }

    public Iterable<Category> findAll() throws Exception {
        return this.categoryRepository.findAll();
    }

    public Category findById(String id) throws Exception {
        var found = this.categoryRepository.findById(UUID.fromString(id));
        if (!found.isPresent()) {
            throw new RuntimeException("Non-existent category");
        }
        return found.get();
    }

    public Page<Category> search(int pageNumber, int pageSize, String searchString, char firstLetter) throws Exception {
        return this.categoryRepository.findBy(searchString, firstLetter, pageNumber, pageSize);
    }
}

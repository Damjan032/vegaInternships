package rs.vegait.timesheet.core.service;


import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.repository.CategoryRepository;

import java.sql.SQLException;
import java.util.UUID;

public class CategoryService implements rs.vegait.timesheet.core.service.BaseService<Category, UUID> {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void create(Category category) throws Exception {
        var foundCategory = this.categoryRepository.findById(category.id());
        if (foundCategory.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.categoryRepository.add(category);
    }

    @Override
    public void update(Category updateObject) throws Exception {
        var foundCategory = this.categoryRepository.findById(updateObject.id());
        if (foundCategory.isPresent()) {
            throw new RuntimeException("Already exists with same id");
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
}

package service;

import models.project.Category;
import repository.CategoryRepository;

import java.util.Optional;
import java.util.UUID;

public class CategoryService implements IService<Category, UUID> {

    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void create(Category category) {
        var foundCategory = this.categoryRepository.findById(category.id());
        if (foundCategory.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.categoryRepository.add(category);
    }

    @Override
    public Optional<Category> readById(UUID id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public void update(Category updateObject) {
        var foundCategory = this.categoryRepository.findById(updateObject.id());
        if (foundCategory.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.categoryRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) {
        var foundCategory = this.categoryRepository.findById(id);
        if (!foundCategory.isPresent()) {
            throw new RuntimeException("Non-existent category");
        }
        this.categoryRepository.remove(id);
    }
}

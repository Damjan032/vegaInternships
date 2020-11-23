package rs.vegait.timesheet.api.factory;

import lombok.Data;
import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.CategoryDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Component
public class CategoryFactory implements Factory<CategoryDto, Category> {


    @Override
    public Category createFromDto(UUID id, CategoryDto categoryDto) {
        return new Category(id, categoryDto.getName());
    }

    @Override
    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.id().toString(), category.name());
    }

    @Override
    public List<CategoryDto> toListDto(Iterable<Category> iterable) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        iterable.forEach(category -> categoryDtoList.add(toDto(category)));
        return categoryDtoList;
    }

    @Override
    public PageDto<CategoryDto> toDtoPage(Page<Category> page) {
        return new PageDto<>(
                toListDto(page.items()),
                page.pageNumber(),
                page.pageSize(),
                page.totalItems()
        );
    }
}

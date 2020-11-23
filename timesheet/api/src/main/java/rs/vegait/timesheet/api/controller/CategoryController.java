package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.vegait.timesheet.api.dto.CategoryDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.api.factory.CategoryFactory;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.service.CategoryService;

import javax.websocket.server.PathParam;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryFactory categoryFactory;

    public CategoryController(CategoryService categoryService, CategoryFactory categoryFactory) {
        this.categoryService = categoryService;
        this.categoryFactory = categoryFactory;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<CategoryDto>> getAll() throws Exception {
        Iterable<Category> employees = categoryService.findAll();
        return new ResponseEntity<>(categoryFactory.toListDto(employees), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> add(@RequestBody CategoryDto employeeDto) throws Exception {
        Category newCategory = categoryFactory.createFromDto(UUID.randomUUID(), employeeDto);
        categoryService.create(newCategory);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto employeeDto) throws Exception {
        Category newCategory = categoryFactory.createFromDto(UUID.fromString(employeeDto.getId()), employeeDto);
        categoryService.update(newCategory);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }


    @GetMapping(path = "/search")
    public ResponseEntity<PageDto<CategoryDto>> getPage(
            @PathParam("pageNumber") Integer pageNumber,
            @PathParam("pageSize") Integer pageSize,
            @PathParam("searchString") String searchString,
            @PathParam("firstLetter") Character firstLetter
    ) throws Exception {

        PageDto<CategoryDto> categoryDtoPageDto = this.categoryFactory.toDtoPage(
                this.categoryService.search(pageNumber, pageSize, searchString, firstLetter));
        return new ResponseEntity<>(categoryDtoPageDto,
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") String id) throws Exception {
        Category category = this.categoryService.findById(id);
        return new ResponseEntity<>(this.categoryFactory.toDto(category), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteClinic(@PathVariable("id") String id) throws Exception {
        this.categoryService.delete(UUID.fromString(id));
        return new ResponseEntity<>("deleted category", HttpStatus.OK);
    }
}

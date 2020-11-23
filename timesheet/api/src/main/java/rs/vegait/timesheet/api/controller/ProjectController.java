package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.api.dto.ProjectDto;
import rs.vegait.timesheet.api.factory.ProjectFactory;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.service.ProjectService;

import javax.websocket.server.PathParam;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectFactory projectFactory;

    public ProjectController(ProjectService projectService, ProjectFactory projectFactory) {
        this.projectService = projectService;
        this.projectFactory = projectFactory;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<ProjectDto>> getAll() throws Exception {
        Iterable<Project> employees = projectService.findAll();
        return new ResponseEntity<>(projectFactory.toListDto(employees), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> add(@RequestBody ProjectDto projectDto) throws Exception {
        Project newProject = projectFactory.createFromDto(UUID.randomUUID(), projectDto);
        projectService.create(newProject);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> update(@RequestBody ProjectDto projectDto) throws Exception {
        Project newProject = projectFactory.createFromDto(UUID.fromString(projectDto.getId()), projectDto);
        projectService.update(newProject);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }


    @GetMapping(path = "/search")
    public ResponseEntity<PageDto<ProjectDto>> getPage(
            @PathParam("pageNumber") Integer pageNumber,
            @PathParam("pageSize") Integer pageSize,
            @PathParam("searchString") String searchString,
            @PathParam("firstLetter") Character firstLetter
    ) throws Exception {

        PageDto<ProjectDto> projectDtoPageDto = this.projectFactory.toDtoPage(
                this.projectService.search(pageNumber, pageSize, searchString, firstLetter));
        return new ResponseEntity<>(projectDtoPageDto,
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProjectDto> getById(@PathVariable("id") String id) throws Exception {
        Project project = this.projectService.findById(id);
        return new ResponseEntity<>(this.projectFactory.toDto(project), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception {
        this.projectService.delete(UUID.fromString(id));
        return new ResponseEntity<>("deleted project", HttpStatus.OK);
    }
}

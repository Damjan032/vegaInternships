package rs.vegait.timesheet.api.factory;

import lombok.Data;
import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.EmployeeDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.api.dto.ProjectDto;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.model.project.ProjectDescription;
import rs.vegait.timesheet.core.model.project.ProjectName;
import rs.vegait.timesheet.core.model.project.ProjectStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Component
public class ProjectFactory implements Factory<ProjectDto, Project> {
    private final EmployeeFactory employeeFactory;
    private final ClientFactory clientFactory;

    @Override
    public Project createFromDto(UUID id, ProjectDto projectDto) {
        ProjectDescription projectDescription = new ProjectDescription("description");
        Optional<ProjectDescription> optionalProjectDescription;
        if (projectDto.getDescription() == null || projectDto.getDescription().trim().equalsIgnoreCase(""))
            optionalProjectDescription = Optional.empty();
        else
            optionalProjectDescription = Optional.of(projectDescription);

        return new Project(id,
                optionalProjectDescription,
                new ProjectName(projectDto.getName()),
                ProjectStatus.valueOf(projectDto.getStatus()),
                this.employeeFactory.createFromDto(UUID.fromString(projectDto.getTeamLead().getId()), projectDto.getTeamLead()),
                this.clientFactory.createFromDto(UUID.fromString(projectDto.getClientDto().getId()), projectDto.getClientDto()));
    }

    @Override
    public ProjectDto toDto(Project project) {
        EmployeeDto employeeDto = this.employeeFactory.toDto(project.teamLead());
        employeeDto.setDailyTimeSheetList(null);
        return new ProjectDto(
                project.id().toString(),
                this.employeeFactory.toDto(project.teamLead()),
                project.name().name(),
                project.status().name(),
                this.clientFactory.toDto(project.client()),
                project.hasDescription() ? project.description().description() : null
        );
    }

    @Override
    public List<ProjectDto> toListDto(Iterable<Project> iterable) {
        List<ProjectDto> projectDtoList = new ArrayList<>();
        iterable.forEach(project -> {
            projectDtoList.add(this.toDto(project));
        });
        return projectDtoList;
    }

    @Override
    public PageDto<ProjectDto> toDtoPage(Page<Project> page) {
        return new PageDto<>(this.toListDto(page.items()),
                page.pageSize(),
                page.pageNumber(),
                page.totalItems()
        );
    }
}

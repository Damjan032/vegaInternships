package rs.vegait.timesheet.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class ProjectDto {
    @NotBlank
    private String id;
    @NotBlank
    private EmployeeDto teamLead;
    @NotBlank
    private String name;
    @NotBlank
    private String status;
    @NotBlank
    private ClientDto clientDto;
    private String description;
}

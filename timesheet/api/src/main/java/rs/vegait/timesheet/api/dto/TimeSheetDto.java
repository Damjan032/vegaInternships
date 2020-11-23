package rs.vegait.timesheet.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class TimeSheetDto {
    private String id;
    @NotBlank
    private double time;
    private String description;
    private double overtime;
    private ProjectDto projectDto;

}

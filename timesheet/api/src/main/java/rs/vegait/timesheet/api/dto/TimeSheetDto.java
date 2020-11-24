package rs.vegait.timesheet.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSheetDto {
    private double time;
    private String description;
    private double overtime;
    private ProjectDto project;
    private CategoryDto categoryDto;

}

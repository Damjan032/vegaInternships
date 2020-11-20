package rs.vegait.timesheet.api.dto;

import javax.validation.constraints.NotBlank;

public class TimeSheetDto {
    @NotBlank
    private String id;
    @NotBlank
    private double time;
    private String description;
    private double overtime;

}

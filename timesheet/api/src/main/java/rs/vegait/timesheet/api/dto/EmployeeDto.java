package rs.vegait.timesheet.api.dto;

import rs.vegait.timesheet.core.model.employee.EmployeeRole;
import rs.vegait.timesheet.core.model.employee.EmployeeStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

@AllArgsConstructor
@Data
public class EmployeeDto {
    @NotBlank
    private String id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank private String name;
    @NotNull
    private Double hoursPerWeek;
    @NotBlank private String email;
    @NotNull private EmployeeRole role;
    @NotNull private EmployeeStatus status;
    @NotNull private boolean wasAccepted;
    private List<DailyTimeSheetDto> dailyTimeSheetList;



}

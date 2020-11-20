package rs.vegait.timesheet.api.dto;

import javax.validation.constraints.NotBlank;

public class CountryDto {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
}
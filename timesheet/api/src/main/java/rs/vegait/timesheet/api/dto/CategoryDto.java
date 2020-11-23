package rs.vegait.timesheet.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
}

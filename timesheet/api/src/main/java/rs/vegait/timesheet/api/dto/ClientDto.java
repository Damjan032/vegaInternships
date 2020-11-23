package rs.vegait.timesheet.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ClientDto {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private int zipCode;
    @NotNull
    private String country;
}

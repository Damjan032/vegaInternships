package rs.vegait.timesheet.api.dto;

import rs.vegait.timesheet.core.model.client.Country;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ClientOutputDto {
    @NotBlank
    private String id;
    @NotBlank private String name;
    @NotBlank private String address;
    @NotBlank private String city;
    @NotBlank private String zipCode;
    @NotNull private Country country;
}

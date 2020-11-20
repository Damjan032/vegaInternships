package rs.vegait.timesheet.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ClientInputDto {
    @NotBlank
    private String id;
    @NotBlank private String name;
    @NotBlank private String address;
    @NotBlank private String city;
    @NotBlank private double zipCode;
    @NotNull private String country;
}

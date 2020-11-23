package rs.vegait.timesheet.api.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String password;
    private String repeatedPassword;
}

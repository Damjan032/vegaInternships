package rs.vegait.timesheet.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DailyTimeSheetDto {
    @NotBlank
    private String id;
    @NotBlank
    private Date day;
    private List<TimeSheetDto> timeSheetDtoList;
    private double dailyWorkTime;

}

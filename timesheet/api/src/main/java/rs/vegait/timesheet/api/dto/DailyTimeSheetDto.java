package rs.vegait.timesheet.api.dto;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class DailyTimeSheetDto {
    @NotBlank
    private String id;
    @NotBlank
    private Date day;
    private List<TimeSheetDto> timeSheetDtoList;

}

package rs.vegait.timesheet.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DailyTimeSheetDto {
    private String day;
    private List<TimeSheetDto> timeSheets;
}

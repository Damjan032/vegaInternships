package rs.vegait.timesheet.api.factory;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.DailyTimeSheetDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class DailyTimeSheetFactory {
    private final EmployeeFactory employeeFactory;
    private final TimeSheetFactory timeSheetFactory;
    private final SimpleDateFormat sdf;

    public DailyTimeSheetFactory(EmployeeFactory employeeFactory, TimeSheetFactory timeSheetFactory) {
        this.employeeFactory = employeeFactory;
        this.timeSheetFactory = timeSheetFactory;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    public DailyTimeSheetDto toDto(DailyTimeSheet dailyTimeSheet) {
        return new DailyTimeSheetDto(
                this.sdf.format(dailyTimeSheet.day()),
                this.timeSheetFactory.toListDto(dailyTimeSheet.timeSheets()));
    }

    public List<DailyTimeSheetDto> toListDto(Iterable<DailyTimeSheet> iterable) {
        List<DailyTimeSheetDto> dailyTimeSheetDtos = new ArrayList<>();
        iterable.forEach(timeSheetResultSet -> {
            dailyTimeSheetDtos.add(this.toDto(timeSheetResultSet));
        });
        return dailyTimeSheetDtos;
    }

    public PageDto<DailyTimeSheetDto> toDtoPage(Page<DailyTimeSheet> page) {
        return null;
    }
}

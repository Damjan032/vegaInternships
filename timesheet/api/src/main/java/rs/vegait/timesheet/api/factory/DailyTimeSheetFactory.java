package rs.vegait.timesheet.api.factory;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.DailyTimeSheetDto;
import rs.vegait.timesheet.api.dto.EmployeeDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheetResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class DailyTimeSheetFactory implements Factory<DailyTimeSheetDto, TimeSheetResultSet> {
    private final EmployeeFactory employeeFactory;
    private final TimeSheetFactory timeSheetFactory;

    public DailyTimeSheetFactory(EmployeeFactory employeeFactory, TimeSheetFactory timeSheetFactory) {
        this.employeeFactory = employeeFactory;
        this.timeSheetFactory = timeSheetFactory;
    }

    public DailyTimeSheet fromDto(UUID id, EmployeeDto employeeDto, Date day) {
        /*return new DailyTimeSheet(
                id,
                this.employeeFactory.createFromDto(UUID.fromString(employeeDto.getId()), employeeDto),
                day,
                timeSheets);*/
        return null;
    }

    @Override
    public TimeSheetResultSet createFromDto(UUID id, DailyTimeSheetDto dailyTimeSheetDto) {
        return null;
    }

    @Override
    public DailyTimeSheetDto toDto(TimeSheetResultSet timeSheetResultSet) {
        return new DailyTimeSheetDto(
                timeSheetResultSet.dailyTimeSheet().id().toString(),
                timeSheetResultSet.dailyTimeSheet().day(),
                this.timeSheetFactory.toListDto(timeSheetResultSet.timeSheets()),
                timeSheetResultSet.dailyTimeOfWorks()
        );
    }

    @Override
    public List<DailyTimeSheetDto> toListDto(Iterable<TimeSheetResultSet> iterable) {
        List<DailyTimeSheetDto> dailyTimeSheetDtos = new ArrayList<>();
        iterable.forEach(timeSheetResultSet -> {
            dailyTimeSheetDtos.add(this.toDto(timeSheetResultSet));
        });
        return dailyTimeSheetDtos;
    }

    @Override
    public PageDto<DailyTimeSheetDto> toDtoPage(Page<TimeSheetResultSet> page) {
        return null;
    }
}
